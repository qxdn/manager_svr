package com.zju.manager_svr.controller;

import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.auth0.jwt.interfaces.Claim;
import com.zju.manager_svr.annotation.LoginRequire;
import com.zju.manager_svr.exception.DeleteUploadException;
import com.zju.manager_svr.model.dto.DoctorInfo;
import com.zju.manager_svr.model.dto.LoginForm;
import com.zju.manager_svr.model.dto.PatientForm;
import com.zju.manager_svr.model.dto.RegisterForm;
import com.zju.manager_svr.model.dto.ReturnBean;
import com.zju.manager_svr.model.dto.UpdateInfoForm;
import com.zju.manager_svr.model.dto.UpdateRoleForm;
import com.zju.manager_svr.model.entity.CTImg;
import com.zju.manager_svr.model.entity.Patient;
import com.zju.manager_svr.model.entity.User;
import com.zju.manager_svr.service.PatientService;
import com.zju.manager_svr.service.UserService;
import com.zju.manager_svr.util.HashUtil;
import com.zju.manager_svr.util.JWTUtil;
import com.zju.manager_svr.valid.UserDetailValidGroup;
import com.zju.manager_svr.valid.UserRoleValidGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ReturnBean register(@RequestBody @Validated RegisterForm form) {
        form.setPassword(HashUtil.encode(form.getPassword()));
        User user = new User(form.getUsername(), form.getPassword(), form.getRealname());
        if (userService.registerUser(user)) {
            return ReturnBean.successReturn(null, "register: 注册成功");
        }
        return ReturnBean.failReturn(null, "注册失败");
    }

    @PostMapping("/login")
    public ReturnBean login(@RequestBody @Validated LoginForm form) {
        User user = userService.findUserByUsername(form.getUsername());
        if (null == user || !HashUtil.verification(form.getPassword(), user.getPassword())) {
            return ReturnBean.failReturn("", "用户名或密码错误");
        }
        String accessToken = JWTUtil.generate_access_token(user.getId());
        String refreshToken = JWTUtil.generate_refresh_token(user.getId());
        Map<String, Object> data = Map.of("access_token", accessToken, "refresh_token", refreshToken);
        return ReturnBean.successReturn(data, "login: 登陆成功");
    }

    @PostMapping("/refreshToken")
    public ReturnBean refreshToken(@RequestBody Map<String, Object> map) {
        String token = (String) map.getOrDefault("refresh_token", "");
        if (token.isBlank()) {
            return ReturnBean.failReturn("", "refreshToken: 参数错误");
        }
        Map<String, Claim> payload = JWTUtil.decode_auth_token(token);
        if (null == payload) {
            return ReturnBean.failReturn("", "refreshToken: 请登陆");
        }
        String accessToken = JWTUtil.generate_access_token(payload.get("user_id").asInt());
        Map<String, Object> data = Map.of("access_token", accessToken);
        return ReturnBean.successReturn(data, "refreshToken: 刷新成功");
    }

    @GetMapping("/logout")
    @LoginRequire
    public ReturnBean logout(HttpSession session) {
        session.removeAttribute("user_id");
        session.invalidate();
        return ReturnBean.successReturn("", "退出登陆");
    }

    @GetMapping("/series")
    public ReturnBean series() {
        String[] uuid = UUID.randomUUID().toString().split("-");
        String series = uuid[uuid.length - 1];
        Map<String, Object> map = Map.of("mac", series);
        return ReturnBean.successReturn(map, "series: 生成随机uuid");
    }

    @GetMapping("/getUser")
    @LoginRequire
    public ReturnBean getUser(HttpSession session) {
        Integer currentId = (Integer) session.getAttribute("user_id");
        // 已登录不可能为空
        User user = userService.findUserByID(currentId);
        String realname = user.getRealname();
        if (null == realname) {
            return ReturnBean.failReturn("", "getUser: 获取信息失败");
        }
        Map<String, Object> data = Map.of("username", realname, "id", user.getId(), "root", user.getUserType());
        return ReturnBean.successReturn(data, "getUser: 获取信息失败");
    }

    @LoginRequire
    @PostMapping("/addPatient")
    public ReturnBean addPatient(@RequestBody @Validated Patient patient, HttpSession session) {
        Integer doctorId = getCurrentDoctor(session);
        Patient patient2 = patientService.findByRecordId(patient.getRecordId());
        String msg;
        if (null != patient2) {
            msg = "病人已存在，已更新数据";
            patient = patient2.update(patient);
        } else {
            msg = "病人已成功添加";
            patient.setDoctorId(doctorId);
        }
        patient = patientService.saveOrUpdate(patient);
        Map<String, Object> data = Map.of("patient", patient);
        return ReturnBean.successReturn(data, msg);
    }

    @LoginRequire
    @GetMapping("/getPatients")
    public ReturnBean getPatients(HttpSession session) {
        Integer doctorId = getCurrentDoctor(session);
        List<Patient> patients = patientService.getAllPatients(doctorId);
        Map<String, Object> data = Map.of("patients", patients);
        return ReturnBean.successReturn(data, "getPatients： 获取病人列表成功");
    }

    @LoginRequire
    @PostMapping("/getPatient")
    public ReturnBean getPatient(@RequestBody @Validated PatientForm form, HttpSession session) {
        Integer id = form.getPatientId();
        Integer doctorID = getCurrentDoctor(session);
        Patient patient = patientService.getPatient(id, doctorID);
        if (null == patient) {
            return ReturnBean.failReturn("", "getPatient: 获取病人失败");
        }
        Map<String, Object> data = Map.of("patient", patient);
        return ReturnBean.successReturn(data, "getPatient: 获取病人成功");
    }

    @PostMapping("/delPatient")
    @LoginRequire
    public ReturnBean deletePatient(@RequestBody @Validated PatientForm form) throws DeleteUploadException {
        Integer id = form.getPatientId();
        if (patientService.deletePatient(id)) {
            return ReturnBean.successReturn("", "delPatient: 删除成功");
        }
        return ReturnBean.failReturn("", "delPatient: 删除失败");
    }

    @PostMapping("/getDetail")
    @LoginRequire
    public ReturnBean getDetail(@RequestBody @Validated PatientForm form, HttpSession session) {
        Integer id = form.getPatientId();
        Integer doctorID = getCurrentDoctor(session);
        Patient patient = patientService.getPatient(id, doctorID);
        if (null == patient) {
            return ReturnBean.failReturn("", "getDetail: 获取病人信息失败");
        }
        List<CTImg> imgs = patientService.getPatientImgs(id);
        Map<String, Object> data = Map.of("patient", patient, "imgs", imgs);
        return ReturnBean.successReturn(data, "getDetail: 获取病人信息成功");
    }

    @PostMapping("/imgList")
    @LoginRequire
    public ReturnBean getImgList(@RequestBody @Validated PatientForm form) {
        Integer id = form.getPatientId();
        List<CTImg> imgs = patientService.getPatientImgs(id);
        Map<String, Object> data = Map.of("imgs", imgs);
        return ReturnBean.successReturn(data, "imgList: 获取图像列表成功");
    }

    @PostMapping("/updateInfo")
    @LoginRequire
    public ReturnBean updateInfo(@RequestBody @Validated UpdateInfoForm form) {
        Patient patient = patientService.getPatient(form.getPatientId());
        if (null == patient) {
            return ReturnBean.failReturn("", "updateInfo: 更新失败");
        }
        patient.update(form);
        patientService.saveOrUpdate(patient);
        return ReturnBean.successReturn("", "updateInfo: 更新成功");
    }

    @GetMapping("/statistics")
    @LoginRequire()
    public ReturnBean statistics(HttpSession session) {
        Integer doctorID = getCurrentDoctor(session);
        User cur = userService.findUserByID(doctorID);
        if (cur.getUserType() != 1) {
            return ReturnBean.failReturn("", "statistics: 权限不足");
        }
        List<User> users = userService.findAllUser();
        List<DoctorInfo> res = new ArrayList<>();
        for (User user : users) {
            List<Patient> patients = patientService.getAllPatients(user.getId());
            res.add(new DoctorInfo(user.getId(), user.getRealname(), patients,
                    userService.getRole(user.getUserType())));
        }
        Map<String, Object> data = Map.of("res", res);
        return ReturnBean.successReturn(data, "statistics: 获取用户列表成功");
    }

    @PostMapping("/userDetail")
    @LoginRequire
    public ReturnBean userDetail(@RequestBody @Validated({ UserDetailValidGroup.class }) UpdateRoleForm form) {
        Integer doctorId = form.getUserID();
        User user = userService.findUserByID(doctorId);
        if (null == user) {
            return ReturnBean.failReturn("", "userDetail: 用户不存在");
        }
        Map<String, Object> data = Map.of("name", user.getUsername(), "realname", user.getRealname(), "role",
                userService.getRole(user.getUserType()));
        return ReturnBean.successReturn(data, "userDetail； 获取用户详细信息成功");
    }

    @PostMapping("/updateRole")
    @LoginRequire
    public ReturnBean updateRole(@RequestBody @Validated({ UserRoleValidGroup.class }) UpdateRoleForm form,
            HttpSession session) {
        Integer cur = getCurrentDoctor(session);
        User doctor = userService.findUserByID(cur);
        if (doctor.getUserType() > form.getRole()) {
            return ReturnBean.failReturn("", "updateRole; 权限不足");
        }
        User user = userService.findUserByID(form.getUserID());
        if (null == user) {
            return ReturnBean.failReturn("", "updateRole； 用户不存在");
        }
        user.setUserType(form.getRole());
        userService.updateUser(user);
        return ReturnBean.successReturn("", "updateRole: 更新权限成功");
    }

    private Integer getCurrentDoctor(HttpSession session) {
        return (Integer) session.getAttribute("user_id");
    }
}

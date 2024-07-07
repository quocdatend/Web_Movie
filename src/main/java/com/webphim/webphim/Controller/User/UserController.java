package com.webphim.webphim.Controller.User;

import com.webphim.webphim.Model.ImageUser;
import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.WatchHistory;
import com.webphim.webphim.Service.*;
import com.webphim.webphim.config.PaymentConfig;
import com.webphim.webphim.Model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ImageUserService imageUserService;
    @Autowired
    private WatchHistoryService watchHistoryService;
    @Autowired
    private AdminMoviesService adminMoviesService;
    @GetMapping("/Profile")
    public String showProfileUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = usersService.getUserByUsername(userDetails.getUsername());
        ImageUser imageUser = imageUserService.getImageUserById(user.getId());
        List<WatchHistory> watchHistory = watchHistoryService.findByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("watchHistory", watchHistory);
        if(imageUser.getUrl()==null) {
            model.addAttribute("Avatar", imageUser.getAvatarDefault());
        } else  {
            model.addAttribute("Avatar", imageUser.getUrl());
        }
        return "User/Profile/index";
    }

    @PostMapping("/UploadImageToCloud")
    public String uploadAvatarUserToCloud(Model model, @RequestParam MultipartFile AvatarUrl, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = usersService.getUserByUsername(userDetails.getUsername());
        ImageUser imageUser = imageUserService.getImageUserById(user.getId());
        imageUserService.updateImageUserById((long) imageUser.getId(), AvatarUrl);
        model.addAttribute("user", user);
        model.addAttribute("Avatar", imageUser.getUrl());
        return "redirect:/User/Profile";
    }
    @GetMapping("/Payment")
    public String createPayment(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        //long amount = Integer.parseInt(req.getParameter("amount"))*100;
        long amount = 99999*100;
        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
        String vnp_IpAddr = PaymentConfig.getIpAddress(req);

        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
        model.addAttribute("PaymentURL", paymentUrl);
        return "User/Payment/Service-pack-premium";
    }

    @GetMapping("/payment/payment-return")
    public String showPaySuccess(@RequestParam(value = "vnp_Amount") String vnp_Amount,
                                 @RequestParam(value = "vnp_BankCode") String vnp_BankCode,
                                 @RequestParam(value = "vnp_CardType") String vnp_CardType,
                                 @RequestParam(value = "vnp_OrderInfo") String vnp_OrderInfo,
                                 @RequestParam(value = "vnp_PayDate") String vnp_PayDate,
                                 @RequestParam(value = "vnp_ResponseCode") String vnp_ResponseCode,
                                 @RequestParam(value = "vnp_TmnCode") String vnp_TmnCode,
                                 @RequestParam(value = "vnp_TransactionNo") String vnp_TransactionNo,
                                 @RequestParam(value = "vnp_TransactionStatus") String vnp_TransactionStatus,
                                 @RequestParam(value = "vnp_TxnRef") String vnp_TxnRef,
                                 @RequestParam(value = "vnp_SecureHash") String vnp_SecureHash,
                                 Model model,@AuthenticationPrincipal UserDetails userDetails ) {
        if(vnp_TransactionStatus.equals("00")) {
            // thêm các tham số
            model.addAttribute("status", true);
            Users user = usersService.getUserByUsername(userDetails.getUsername());
            user.setPre(true);
            usersService.saveUser(user);
            usersService.setPremiumRole(userDetails.getUsername());
        } else {
            // tạo thêm các tham số khi thanh toán thất bại tại đây
            model.addAttribute("status", false);
        }
        return "User/Payment/Successfully";
    }
    @SneakyThrows
    @GetMapping("/SaveMovie")
    public String saveMovieHistory(@RequestParam String id, @AuthenticationPrincipal UserDetails userDetails) {
        Users users = usersService.getUserByUsername(userDetails.getUsername());
        Movies movies = adminMoviesService.findid(Long.valueOf(id));
        LocalTime localTime = LocalTime.now();
        if(movies == null) {
            return "redirect:/Home";
        }
        if(watchHistoryService.checkHistory(users, movies)) {
            WatchHistory watchHistory = new WatchHistory();
            watchHistory.setUsers(users);
            watchHistory.setMovie(movies);
            watchHistory.setTime(Time.valueOf(localTime));
            watchHistoryService.saveWatchHistory(watchHistory);
        }
        return "redirect:/Home";
    }
}

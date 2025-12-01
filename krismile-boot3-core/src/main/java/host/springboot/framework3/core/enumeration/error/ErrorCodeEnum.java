package host.springboot.framework3.core.enumeration.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import host.springboot.framework3.core.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;

/**
 * 错误码枚举
 *
 * <ul>
 *     <li><b>00000</b> - 正确执行后返回</li>
 *     <li><b>A0001</b> - 用户端错误
 *         <ul>
 *             <li><b>A0100</b> - 用户注册错误</li>
 *             <li><b>A0200</b> - 用户登录异常</li>
 *             <li><b>A0300</b> - 访问权限异常</li>
 *             <li><b>A0400</b> - 用户请求参数错误</li>
 *             <li><b>A0500</b> - 用户请求服务异常</li>
 *             <li><b>A0600</b> - 用户资源异常</li>
 *             <li><b>A0700</b> - 用户上传文档异常</li>
 *             <li><b>A0800</b> - 用户当前版本异常</li>
 *             <li><b>A0900</b> - 用户隐私未授权</li>
 *             <li><b>A1000</b> - 用户设备异常</li>
 *         </ul>
 *     </li>
 *     <li><b>B0001</b> - 系统执行出错
 *         <ul>
 *             <li><b>B0100</b> - 系统执行超时</li>
 *             <li><b>B0200</b> - 系统容灾功能被触发</li>
 *             <li><b>B0300</b> - 系统资源异常</li>
 *         </ul>
 *     </li>
 *     <li><b>C0001</b> - 调用第三方服务出错
 *         <ul>
 *             <li><b>C0100</b> - 中间件服务出错</li>
 *             <li><b>C0200</b> - 第三方系统执行超时</li>
 *             <li><b>C0300</b> - 数据库服务出错</li>
 *             <li><b>C0400</b> - 第三方容灾系统被触发</li>
 *             <li><b>C0500</b> - 通知服务出错</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author JiYinchuan
 * @since 0.1.0
 */
@AllArgsConstructor
public enum ErrorCodeEnum implements BaseEnum<String> {

    // -------------------------------- 正确执行后返回 --------------------------------

    /**
     * 一切 ok
     */
    OK("00000", "OK"),

    // -------------------------------- 一级宏观错误码 --------------------------------

    /**
     * 用户端错误
     */
    USER_CLIENT_ERROR("A0001", "用户端错误"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户注册错误
     */
    USER_REGISTER_ERROR("A0100", "用户注册错误"),

    /**
     * 用户未同意隐私协议
     */
    USER_NOT_AGREE_PRIVACY_AGREEMENT("A0101", "用户未同意隐私协议"),

    /**
     * 注册国家或地区受限
     */
    RESTRICTED_COUNTRY_OR_REGION_OF_REGISTRATION("A0102", "注册国家或地区受限"),

    /**
     * 用户名校验失败
     */
    USERNAME_VERIFY_FAILED("A0110", "用户名校验失败"),

    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXISTS("A0111", "用户名已存在"),

    /**
     * 用户名包含敏感词
     */
    USERNAME_CONTAINS_SENSITIVE_WORDS("A0112", "用户名包含敏感词"),

    /**
     * 用户名包含特殊字符
     */
    USERNAME_CONTAINS_SPECIAL_CHARACTERS("A0113", "用户名包含特殊字符"),

    /**
     * 密码校验失败
     */
    PASSWORD_VERIFY_FAILED("A0120", "密码校验失败"),

    /**
     * 密码长度不够
     */
    PASSWORD_LENGTH_NOT("A0121", "密码长度不够"),

    /**
     * 密码强度不够
     */
    PASSWORD_NOT_STRONG("A0122", "密码强度不够"),

    /**
     * 校验码输入错误
     */
    CODE_INPUT_ERROR("A0130", "校验码输入错误"),

    /**
     * 短信校验码输入错误
     */
    SMS_CODE_INPUT_ERROR("A0131", "短信校验码输入错误"),

    /**
     * 邮件校验码输入错误
     */
    EMAIL_CODE_INPUT_ERROR("A0132", "邮件校验码输入错误"),

    /**
     * 语音校验码输入错误
     */
    VOICE_CODE_INPUT_ERROR("A0133", "语音校验码输入错误"),

    /**
     * 用户证件异常
     */
    USER_CERTIFICATE_ERROR("A0140", "用户证件异常"),

    /**
     * 用户证件类型未选择
     */
    USER_CERTIFICATE_TYPE_NOT_SELECTED("A0141", "用户证件类型未选择"),

    /**
     * 大陆身份证编号校验非法
     */
    MAINLAND_ID_CARD_VERIFY_ILLEGAL("A0142", "大陆身份证编号校验非法"),

    /**
     * 护照编号校验非法
     */
    ILLEGAL_PASSPORT_VERIFY("A0143", "护照编号校验非法"),

    /**
     * 军官证编号校验非法
     */
    MILITARY_OFFICER_ID_VERIFY_ILLEGAL("A0144", "军官证编号校验非法"),

    /**
     * 用户基本信息校验失败
     */
    USER_BASIC_INFO_VERIFY_FAILED("A0150", "用户基本信息校验失败"),

    /**
     * 手机格式校验失败
     */
    PHONE_FORMAT_VERIFY_FAILED("A0151", "手机格式校验失败"),

    /**
     * 地址格式校验失败
     */
    ADDRESS_FORMAT_VERIFY_FAILED("A0152", "地址格式校验失败"),

    /**
     * 邮箱格式校验失败
     */
    EMAIL_FORMAT_VERIFY_FAILED("A0153", "邮箱格式校验失败"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户登录异常
     */
    USER_LOGIN_ERROR("A0200", "用户登录异常"),

    /**
     * 用户账户不存在
     */
    USER_ACCOUNT_NOT_EXIST("A0201", "用户账户不存在"),

    /**
     * 用户账户被冻结
     */
    USER_ACCOUNT_FROZEN("A0202", "用户账户被冻结"),

    /**
     * 用户账户已作废
     */
    USER_ACCOUNT_INVALID("A0203", "用户账户已作废"),

    /**
     * 用户账户已过期
     */
    USER_ACCOUNT_EXPIRE("A0204", "用户账户已过期"),

    /**
     * 用户凭证已过期
     */
    USER_CREDENTIALS_EXPIRE("A0205", "用户凭证已过期"),

    /**
     * 用户密码错误
     */
    USER_PASSWORD_VERIFY_FAILED("A0210", "用户密码错误"),

    /**
     * 用户输入密码错误次数超限
     */
    USER_INPUT_ERROR_PASSWORD_OVERRUN("A0211", "用户输入密码错误次数超限"),

    /**
     * 用户身份校验失败
     */
    USER_IDENTITY_VERIFICATION_FAILED("A0220", "用户身份校验失败"),

    /**
     * 用户指纹识别失败
     */
    USER_FINGERPRINT_VERIFICATION_FAILED("A0221", "用户指纹识别失败"),

    /**
     * 用户面容识别失败
     */
    USER_FACE_VERIFICATION_FAILED("A0222", "用户面容识别失败"),

    /**
     * 用户未获得第三方登录授权
     */
    USER_NOT_AUTHORIZED_BY_THIRTY_PARTY("A0223", "用户未获得第三方登录授权"),

    /**
     * 用户登录已过期
     */
    USER_LOGIN_EXPIRED("A0230", "用户登录已过期"),

    /**
     * 用户验证码错误
     */
    USER_CODE_VERIFICATION_FAILED("A0240", "用户验证码错误"),

    /**
     * 用户验证码尝试次数超限
     */
    USER_INPUT_ERROR_CODE_OVERRUN("A0241", "用户验证码尝试次数超限"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 访问权限异常
     */
    ACCESS_PERMISSION_ERROR("A0300", "访问权限异常"),

    /**
     * 访问未授权
     */
    ACCESS_UNAUTHORIZED("A0301", "访问未授权"),

    /**
     * 正在授权中
     */
    AUTHORIZING("A0302", "正在授权中"),

    /**
     * 用户授权申请被拒绝
     */
    USER_AUTHORITY_REJECTED("A0303", "用户授权申请被拒绝"),

    /**
     * 因访问对象隐私设置被拦截
     */
    BLOCKED_DUE_TO_PRIVACY_SETTINGS("A0310", "因访问对象隐私设置被拦截"),

    /**
     * 授权已过期
     */
    AUTHORITY_EXPIRED("A0311", "授权已过期"),

    /**
     * 无权限使用 API
     */
    NO_PERMISSION_TO_USE_API("A0312", "无权限使用 API"),

    /**
     * 用户访问被拦截
     */
    USER_ACCESS_BLOCKED("A0320", "用户访问被拦截"),

    /**
     * 黑名单用户
     */
    BLACKLISTED_USER("A0321", "黑名单用户"),

    /**
     * 账号被冻结
     */
    ACCOUNT_IS_FROZEN("A0322", "账号被冻结"),

    /**
     * 非法 IP 地址
     */
    ILLEGAL_IP_ADDRESS("A0323", "非法 IP 地址"),

    /**
     * 网关访问受限
     */
    GATEWAY_ACCESS_RESTRICTED("A0324", "网关访问受限"),

    /**
     * 地域黑名单
     */
    BLACKLISTED_REGIONAL("A0325", "地域黑名单"),

    /**
     * 服务已欠费
     */
    SERVICE_ARREARS("A0330", "服务已欠费"),

    /**
     * 用户签名异常
     */
    USER_SIGNATURE_ERROR("A0340", "用户签名异常"),

    /**
     * RSA 签名错误
     */
    RSA_SIGNATURE_ERROR("A0341", "RSA 签名错误"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户请求参数错误
     */
    USER_PARAMETER_ERROR("A0400", "用户请求参数错误"),

    /**
     * 包含非法恶意链接地址
     */
    CONTAINS_ILLEGAL_LINKS("A0401", "包含非法恶意链接地址"),

    /**
     * 无效的用户输入
     */
    INVALID_USER_INPUT("A0402", "无效的用户输入"),

    /**
     * 请求必填参数为空
     */
    PARAMETER_EMPTY("A0410", "请求必填参数为空"),

    /**
     * 用户订单号为空
     */
    USER_ORDER_NUMBER_EMPTY("A0411", "用户订单号为空"),

    /**
     * 订购数量为空
     */
    ORDER_QUANTITY_EMPTY("A0412", "订购数量为空"),

    /**
     * 缺少时间戳参数
     */
    TIMESTAMP_PARAMETER_EMPTY("A0413", "缺少时间戳参数"),

    /**
     * 非法的时间戳参数
     */
    ILLEGAL_TIMESTAMP_PARAMETER("A0414", "非法的时间戳参数"),

    /**
     * 请求参数值超出允许的范围
     */
    PARAMETER_EXCEEDS_RANGE("A0420", "请求参数值超出允许的范围"),

    /**
     * 参数格式不匹配
     */
    PARAMETER_FORMAT_NOT_MATCH("A0421", "参数格式不匹配"),

    /**
     * 地址不在服务范围
     */
    ADDRESS_NOT_IN_SERVICE("A0422", "地址不在服务范围"),

    /**
     * 时间不在服务范围
     */
    TIME_NOT_IN_SERVICE("A0423", "时间不在服务范围"),

    /**
     * 金额超出限制
     */
    AMOUNT_EXCEEDS_LIMIT("A0424", "金额超出限制"),

    /**
     * 数量超出限制
     */
    COUNT_EXCEEDS_LIMIT("A0425", "数量超出限制"),

    /**
     * 请求批量处理总个数超出限制
     */
    BATCH_PROCESSING_EXCEEDS_LIMIT("A0426", "请求批量处理总个数超出限制"),

    /**
     * 请求 JSON 解析失败
     */
    JSON_PARSING_FAILED("A0427", "请求 JSON 解析失败"),

    /**
     * 用户输入内容非法
     */
    USER_INPUT_ILLEGAL("A0430", "用户输入内容非法"),

    /**
     * 包含违禁敏感词
     */
    PROHIBITED_SENSITIVE_WORDS("A0431", "包含违禁敏感词"),

    /**
     * 图片包含违禁信息
     */
    PICTURE_PROHIBITED_INFORMATION("A0432", "图片包含违禁信息"),

    /**
     * 文件侵犯版权
     */
    FILE_INFRINGES_COPYRIGHT("A0433", "文件侵犯版权"),

    /**
     * 用户操作异常
     */
    USER_OPERATION_ERROR("A0440", "用户操作异常"),

    /**
     * 用户支付超时
     */
    USER_PAYMENT_TIMEOUT("A0441", "用户支付超时"),

    /**
     * 确认订单超时
     */
    CONFIRM_ORDER_TIMEOUT("A0442", "确认订单超时"),

    /**
     * 订单已关闭
     */
    ORDER_CLOSED("A0443", "订单已关闭"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户请求服务异常
     */
    USER_SERVICE_ERROR("A0500", "用户请求服务异常"),

    /**
     * 请求次数超出限制
     */
    NUMBER_OF_REQUESTS_EXCEEDS_LIMIT("A0501", "请求次数超出限制"),

    /**
     * 请求并发数超出限制
     */
    CONCURRENCY_EXCEEDS_LIMIT("A0502", "请求并发数超出限制"),

    /**
     * 用户操作请等待
     */
    USER_OPERATION_WAIT("A0503", "用户操作请等待"),

    /**
     * WebSocket 连接异常
     */
    WEBSOCKET_CONNECTION_ERROR("A0504", "WebSocket 连接异常"),

    /**
     * WebSocket 连接断开
     */
    WEBSOCKET_DISCONNECT("A0505", "WebSocket 连接断开"),

    /**
     * 用户重复请求
     */
    USER_REPEAT_REQUEST("A0506", "用户重复请求"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户资源异常
     */
    USER_RESOURCE_ERROR("A0600", "用户资源异常"),

    /**
     * 账户余额不足
     */
    INSUFFICIENT_BALANCE("A0601", "账户余额不足"),

    /**
     * 用户磁盘空间不足
     */
    USER_INSUFFICIENT_DISK_SPACE("A0602", "用户磁盘空间不足"),

    /**
     * 用户内存空间不足
     */
    USER_INSUFFICIENT_MEMORY_SPACE("A0603", "用户内存空间不足"),

    /**
     * 用户 OSS 容量不足
     */
    USER_INSUFFICIENT_OSS_CAPACITY("A0604", "用户 OSS 容量不足"),

    /**
     * 用户配额已用光
     */
    USER_QUOTA_EXHAUSTED("A0605", "用户配额已用光"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户上传文件异常
     */
    USER_UPLOAD_FILE_ERROR("A0700", "用户上传文件异常"),

    /**
     * 用户上传文件类型不匹配
     */
    USER_UPLOAD_FILE_TYPE_MISMATCH("A0701", "用户上传文件类型不匹配"),

    /**
     * 用户上传文件太大
     */
    USER_UPLOAD_FILE_SIZE_TOO_LARGE("A0702", "用户上传文件太大"),

    /**
     * 用户上传图片太大
     */
    USER_UPLOAD_IMAGE_SIZE_TOO_LARGE("A0703", "用户上传图片太大"),

    /**
     * 用户上传视频太大
     */
    USER_UPLOAD_VIDEO_SIZE_TOO_LARGE("A0704", "用户上传视频太大"),

    /**
     * 用户上传压缩文件太大
     */
    USER_UPLOAD_COMPRESSED_FILE_SIZE_TOO_LARGE("A0705", "用户上传压缩文件太大"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户当前版本异常
     */
    USER_VERSION_ERROR("A0800", "用户当前版本异常"),

    /**
     * 用户安装版本与系统不匹配
     */
    USER_VERSION_MISMATCH("A0801", "用户安装版本与系统不匹配"),

    /**
     * 用户安装版本过低
     */
    USER_VERSION_IS_TOO_LOW("A0802", "用户安装版本过低"),

    /**
     * 用户安装版本过高
     */
    USER_VERSION_IS_TOO_HIGH("A0803", "用户安装版本过高"),

    /**
     * 用户安装版本已过期
     */
    USER_VERSION_IS_EXPIRED("A0804", "用户安装版本已过期"),

    /**
     * 用户 API 请求版本不匹配
     */
    USER_API_VERSION_MISMATCH("A0805", "用户 API 请求版本不匹配"),

    /**
     * 用户 API 请求版本过高
     */
    USER_API_VERSION_IS_TOO_HIGH("A0806", "用户 API 请求版本过高"),

    /**
     * 用户 API 请求版本过低
     */
    USER_API_VERSION_IS_TOO__LOW("A0807", "用户 API 请求版本过低"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户隐私未授权异常
     */
    USER_PRIVACY_UNAUTHORIZED_ERROR("A0900", "用户隐私未授权异常"),

    /**
     * 用户隐私未签署
     */
    USER_PRIVACY_UNSIGNED("A0901", "用户隐私未签署"),

    /**
     * 用户摄像头未授权
     */
    USER_UNAUTHORIZED_CAMERA("A0902", "用户摄像头未授权"),

    /**
     * 用户相机未授权
     */
    USER_CAMERA_NOT_AUTHORIZED("A0903", "用户相机未授权"),

    /**
     * 用户图片库未授权
     */
    USER_ALBUM_NOT_AUTHORIZED("A0904", "用户图片库未授权"),

    /**
     * 用户文件未授权
     */
    USER_FILE_NOT_AUTHORIZED("A0905", "用户文件未授权"),

    /**
     * 用户位置信息未授权
     */
    USER_LOCATION_NOT_AUTHORIZED("A0906", "用户位置信息未授权"),

    /**
     * 用户通讯录未授权
     */
    USER_ADDRESS_BOOK_NOT_AUTHORIZED("A0907", "用户通讯录未授权"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 用户设备异常
     */
    USER_DEVICE_ERROR("A1000", "用户设备异常"),

    /**
     * 用户相机异常
     */
    USER_CAMERA_ERROR("A1001", "用户相机异常"),

    /**
     * 用户麦克风异常
     */
    USER_MICROPHONE_ERROR("A1002", "用户麦克风异常"),

    /**
     * 用户听筒异常
     */
    USER_EARPIECE_ERROR("A1003", "用户听筒异常"),

    /**
     * 用户扬声器异常
     */
    USER_SPEAKER_ERROR("A1004", "用户扬声器异常"),

    /**
     * 用户 GPS 定位异常
     */
    USER_POSITIONING_ERROR("A1005", "用户 GPS 定位异常"),

    // -------------------------------- 一级宏观错误码 --------------------------------

    /**
     * 系统执行出错
     */
    SYSTEM_EXECUTION_ERROR("B0001", "系统执行出错"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 系统执行超时
     */
    SYSTEM_EXECUTION_TIMEOUT("B0100", "系统执行超时"),

    /**
     * 系统订单处理超时
     */
    SYSTEM_EXECUTION_ORDER_TIMEOUT("B0101", "系统订单处理超时"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 系统容灾功能被触发
     */
    SYSTEM_TRIGGER_DISASTER_RECOVERY("B0200", "系统容灾功能被触发"),

    /**
     * 系统限流
     */
    SYSTEM_TRIGGER_LIMITING("B0210", "系统限流"),

    /**
     * 系统功能降级
     */
    SYSTEM_TRIGGER_DOWNGRADE("B0220", "系统功能降级"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 系统资源异常
     */
    SYSTEM_RESOURCE_ERROR("B0300", "系统资源异常"),

    /**
     * 系统资源耗尽
     */
    SYSTEM_RESOURCE_EXHAUSTION("B0310", "系统资源耗尽"),

    /**
     * 系统磁盘空间耗尽
     */
    SYSTEM_INSUFFICIENT_DISK_SPACE("B0311", "系统磁盘空间耗尽"),

    /**
     * 系统内存耗尽
     */
    SYSTEM_INSUFFICIENT_MEMORY_SPACE("B0312", "系统内存耗尽"),

    /**
     * 文件句柄耗尽
     */
    FILE_HANDLE_EXHAUSTED("B0313", "文件句柄耗尽"),

    /**
     * 系统连接池耗尽
     */
    SYSTEM_CONNECTION_POOL_EXHAUSTED("B0314", "系统连接池耗尽"),

    /**
     * 系统线程池耗尽
     */
    SYSTEM_THREAD_POOL_EXHAUSTED("B0315", "系统线程池耗尽"),

    /**
     * 系统资源访问异常
     */
    SYSTEM_RESOURCE_ACCESS_ERROR("B0320", "系统资源访问异常"),

    /**
     * 系统读取磁盘文件失败
     */
    SYSTEM_READ_DISK_FILE_ERROR("B0321", "系统读取磁盘文件失败"),

    // -------------------------------- 一级宏观错误码 --------------------------------

    /**
     * 系统读取磁盘文件失败
     */
    CALLING_THIRD_SERVICE_ERROR("C0001", "调用第三方服务出错"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 中间件服务出错
     */
    MIDDLEWARE_SERVICE_ERROR("C0100", "中间件服务出错"),

    /**
     * 服务出错
     */
    RPC_SERVICE_ERROR("C0110", "RPC 服务出错"),

    /**
     * RPC 服务未找到
     */
    RPC_SERVICE_NOT_FOUND("C0111", "RPC 服务未找到"),

    /**
     * RPC 服务未注册
     */
    RPC_SERVICE_NOT_REGISTER("C0112", "RPC 服务未注册"),

    /**
     * 接口不存在
     */
    API_NOT_FOUND("C0113", "接口不存在"),

    /**
     * 消息服务出错
     */
    MESSAGE_SERVICE_ERROR("C0120", "消息服务出错"),

    /**
     * 消息投递出错
     */
    MESSAGE_SEND_ERROR("C0121", "消息投递出错"),

    /**
     * 消息消费出错
     */
    MESSAGE_CONSUME_ERROR("C0122", "消息消费出错"),

    /**
     * 消息订阅出错
     */
    MESSAGE_SUBSCRIBE_ERROR("C0123", "消息订阅出错"),

    /**
     * 消息分组未查到
     */
    MESSAGE_GROUP_NOT_FOUND("C0124", "消息分组未查到"),

    /**
     * 缓存服务出错
     */
    CACHE_SERVICE_ERROR("C0130", "缓存服务出错"),

    /**
     * key 长度超过限制
     */
    CACHE_KEY_EXCEED_LIMIT("C0131", "key 长度超过限制"),

    /**
     * value 长度超过限制
     */
    CACHE_VALUE_EXCEED_LIMIT("C0132", "value 长度超过限制"),

    /**
     * 存储容量已满
     */
    CACHE_STORAGE_FULL("C0133", "存储容量已满"),

    /**
     * 不支持的数据格式
     */
    CACHE_NOT_SUPPORT_DATA_FORMAT("C0134", "不支持的数据格式"),

    /**
     * 配置服务出错
     */
    CONFIG_SERVICE_ERROR("C0140", "配置服务出错"),

    /**
     * 网络资源服务出错
     */
    NETWORK_RESOURCE_SERVICE_ERROR("C0150", "网络资源服务出错"),

    /**
     * VPN 服务出错
     */
    VPN_SERVICE_ERROR("C0151", "VPN 服务出错"),

    /**
     * CDN 服务出错
     */
    CDN_SERVICE_ERROR("C0152", "CDN 服务出错"),

    /**
     * 域名解析服务出错
     */
    DNS_SERVICE_ERROR("C0153", "域名解析服务出错"),

    /**
     * 网关服务出错
     */
    GATEWAY_SERVICE_ERROR("C0154", "网关服务出错"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 第三方系统执行超时
     */
    THIRD_EXECUTE_TIMEOUT("C0200", "第三方系统执行超时"),

    /**
     * RPC 执行超时
     */
    RPC_EXECUTE_TIMEOUT("C0210", "RPC 执行超时"),

    /**
     * 消息投递超时
     */
    MESSAGE_SEND_TIMEOUT("C0220", "消息投递超时"),

    /**
     * 缓存服务超时
     */
    CACHE_SERVICE_TIMEOUT("C0230", "缓存服务超时"),

    /**
     * 配置服务超时
     */
    CONFIG_SERVICE_TIMEOUT("C0240", "配置服务超时"),

    /**
     * 数据库服务超时
     */
    DATABASE_SERVICE_TIMEOUT("C0250", "数据库服务超时"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 数据库服务出错
     */
    DATABASE_SERVICE_ERROR("C0300", "数据库服务出错"),

    /**
     * 表不存在
     */
    DATABASE_TABLE_NOT_EXISTS("C0311", "表不存在"),

    /**
     * 列不存在
     */
    DATABASE_COLUMN_NOT_EXISTS("C0312", "列不存在"),

    /**
     * 多表关联中存在多个相同名称的列
     */
    DATABASE_COLUMN_REPEAT("C0321", "多表关联中存在多个相同名称的列"),

    /**
     * 数据库死锁
     */
    DATABASE_DEADLOCK("C0331", "数据库死锁"),

    /**
     * 主键冲突
     */
    DATABASE_PRIMARY_KEY_CONFLICT("C0341", "主键冲突"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 第三方容灾系统被触发
     */
    THIRD_DISASTER_RECOVERY_TRIGGER("C0400", "第三方容灾系统被触发"),

    /**
     * 第三方系统限流
     */
    THIRD_FLOW_CONTROL("C0401", "第三方系统限流"),

    /**
     * 第三方功能降级
     */
    THIRD_DOWNGRADE("C0402", "第三方功能降级"),

    // -------------------------------- 二级宏观错误码 --------------------------------

    /**
     * 通知服务出错
     */
    NOTICE_SERVICE_ERROR("C0500", "通知服务出错"),

    /**
     * 短信提醒服务失败
     */
    SMS_SERVICE_ERROR("C0501", "短信提醒服务失败"),

    /**
     * 语音提醒服务失败
     */
    VOICE_SERVICE_ERROR("C0502", "语音提醒服务失败"),

    /**
     * 邮件提醒服务失败
     */
    EMAIL_SERVICE_ERROR("C0503", "邮件提醒服务失败");

    /**
     * 枚举值
     */
    private final String value;

    /**
     * 枚举信息
     */
    private final String reasonPhrase;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * 通过枚举值解析枚举
     *
     * @param value 枚举值
     * @return 枚举
     * @since 0.1.0
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static @Nullable ErrorCodeEnum parse(@Nullable String value) {
        return BaseEnum.parse(value, ErrorCodeEnum.class);
    }
}

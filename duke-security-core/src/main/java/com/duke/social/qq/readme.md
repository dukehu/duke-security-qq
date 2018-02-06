开发qq认证流程：

1、Api
    也就是QQ接口，此接口中只有一个方法就是得到qq的用户信息，QQUserInfo getUserInfo();这个方法。
    新建一个QQ接口的实现类，QQImpl,此接口实现QQ接口，实现接口中的获取qq用户信息的方法；继承AbstractOAuth2ApiBinding抽象类
    /**
     * Created duke on 2018/1/9
     */
    public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    
        private static final Logger logger = LoggerFactory.getLogger(QQImpl.class);
    
        // 获取openId的地址
        private static final String QQ_URL_GET_OPENID =
                "https://graph.qq.com/oauth2.0/me?access_token=%s";
        // 获取用户信息的地址（access_token由父类提供）
        private static final String QQ_URL_GET_USER_INFO =
                "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
        private ObjectMapper objectMapper = new ObjectMapper();
    
        /**
         * 配置文件得到的
         */
        private String appId;
    
        /**
         * 请求：URL_GET_OPENID返回
         */
        private String openId;
    
        // 构造函数
        public QQImpl(String accessToken, String appId) {
            // accessToken通过查询参数来携带
            super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
            this.appId = appId;
    
            String url = String.format(QQ_URL_GET_OPENID, accessToken);
    
            // 获取openId
            String result = getRestTemplate().getForObject(url, String.class);
            logger.info("【QQImpl】 QQ_URL_GET_OPENID={} result={}", QQ_URL_GET_OPENID, result);
            this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
        }
    
        @Override
        public QQUserInfo getUserInfo() {
            String url = String.format(QQ_URL_GET_USER_INFO, appId, openId);
    
            // 获取用户信息
            String result = getRestTemplate().getForObject(url, String.class);
    
            logger.info("【QQImpl】 QQ_URL_GET_USER_INFO={} result={}", QQ_URL_GET_USER_INFO, result);
            try {
                return objectMapper.readValue(result, QQUserInfo.class);
            } catch (IOException e) {
                throw new RuntimeException("获取用户信息异常！");
            }
        }
    }
    
2、实现OAuth2Operations接口
    spring social默认提供了一个此接口的实现类（OAuth2Template），可以先使用这个。
    
3、使用Api、OAuth2Operations构建ServiceProvider
    新建一个QQServiceProvider类，此类需要继承AbstractOAuth2ServiceProvider抽象类
    
    /**
     * Created duke on 2018/1/9
     */
    public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    
        private static final Logger logger = LoggerFactory.getLogger(QQServiceProvider.class);
    
        /**
         * 认证第一步，将用户导向认证服务器、第二步，认证服务器给第三方应用返回一个授权码的地址
         */
        private static final String QQ_URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    
        /**
         * 获取access_token的地址
         */
        private static final String QQ_URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
    
    
        private String appId;
    
        /**
         * Create a new {@link OAuth2ServiceProvider}.
         */
        public QQServiceProvider(String appId, String appSecret) {
            super(new OAuth2Template(appId, appSecret, QQ_URL_AUTHORIZE, QQ_URL_ACCESS_TOKEN));
        }
    
        @Override
        public QQ getApi(String accessToken) {
            return new QQImpl(accessToken, appId);
        }
    }
    
4、ApiAdapter
    新建QQApiAdapter类，此类的作用是将服务提供商（qq）获得的个性化的用户信息与spring social标准的用户做一个适配
    
    

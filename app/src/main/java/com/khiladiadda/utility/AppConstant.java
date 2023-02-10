package com.khiladiadda.utility;

import android.os.Environment;

import java.io.File;

/**
 * Created by Harsh on 4/20/2019.
 */

public class AppConstant {
    public static final long TIME_OUT = 30;
    public static final long SUREPASS_TIME_OUT = 120;
    //PRODUCTION SERVER
    //public static final String URL = "aHR0cHM6Ly9wcm9kLmFwaS5wcW12aHFxcndpenBnaGpmYmhzYW96ZWRpcWRlY25td2t3cnRobC5rLWFkZGEuY29t";

//       public static final String URL = "aHR0cHM6Ly9wcm9kLmFwaS5wcW12aHFxcndpenBnaGpmYmhzYW96ZWRpcWRlY25td2t3cnRobC5rLWFkZGEuY29tLw==";
//     public static final String URL = "aHR0cHM6Ly91YXQuYXBpLmtoaWxhZGlhZGRhLmNvbS8=";

    public static final String URL = "aHR0cHM6Ly9xYS5hcGkua2hpbGFkaWFkZGEuY29tLw==";//QA


    //     public static final String URL = "aHR0cHM6Ly9xYS5hcGkua2hpbGFkaWFkZGEuY29tLw==";
    public static final String URL_HOME = "https://www.khiladiadda.com/";
    public static final String PAN_URL = "https://sandbox.veri5digital.com/service/api/1.0/";
    public static final String VERIFY_PAN = "verifyUserIdDoc";
    public static final String VERIFI_AADHAR_URL = "https://prod.veri5digital.com/okyc/api/v1.0/";
    public static final String SUREPASS_VERIFI_AADHAR_URL = "https://kyc-api.aadhaarkyc.io/api/v1/aadhaar-v2/";
    public static final String CRIC_API_URL = "https://cricapi.com/api/";
    public static final String CRIC_API_KEY = "Nj0cL8Ik0RdWryYFaBxJ1tuuVG43";
    public static final String CRIC_API_SCORCE = "cricketScore";
    public static final String PLAYER_STATS = "playerStats";
    public static final String TEXT_TOKEN = "x-access-token";
    public static final String TEXT_VERSION_NO = "app_version";
    public static final String TEXT_CONTENT_TYPE = "Content-Type";
    public static final String TEXT_CONTENT_TYPE_VALUE = "application/json";
    public static final String TEXT_ENC_KEY = "key_enc_";
    public static final String TEXT_KEY_NEW = "x-lru-yek";
    public static final String TEXT_ACCESS_KEY_EASEBUZZ = "access_key";
    public static final String TEXT_PAY_MODE = "pay_mode";
    public static final String TEXT_PAY_MODE_VALUE = "production";
    public static final String API_REGISTER = "auth/register";
    public static final String API_VERIFY_OTP = "auth/verifyotp";
    public static final String API_RESEND_OTP = "auth/resendotp";
    public static final String API_GMAIL_REGISTER = "auth/gmail_register";
    public static final String API_FB_REGISTER = "auth/fb_register";
    public static final String API_LOGIN = "auth/login";
    public static final String API_LOGIN_VERIFY_OTP = "auth/verify_password_otp/login";
    public static final String API_GMAIL_LOGIN = "auth/gmail_login";
    public static final String API_FB_LOGIN = "auth/fb_login";
    public static final String API_FORGOT_PASSWORD = "auth/forgotpassword";

    public static final String API_FP_VERIFY_OTP = "auth/verifyforgotpasswordotp";
    public static final String API_RESET_PASSWORD = "auth/reset-password";

    public static final String API_CHANGE_PASSWORD = "auth/changepassword";
    public static final String API_SEND_OTP = "user/send_otp";
    public static final String API_CHANGE_MOBILE = "user/change_mobile";

    public static final String API_FAN_BATTLE = "user/cricket-fantasy-matches";
    public static final String API_BATTLE = "user/cricket-fantasy-matches-battle";
    public static final String API_GROUP = "user/cricket-fantasy-battle-group";
    public static final String API_JOIN_GROUP = "user/join-cricket-fantasy-matches-battle/{id}";
    public static final String API_MY_FAN_BATTLE = "user/cricket-fantasy-my-matches";

    public static final String API_CREATE_GROUP = "user/fantasy/create-battle-group";
    public static final String API_MY_BATTLE = "user/fantasy/fetch-my-battles";
    public static final String API_UPDATE_BATTLE = "user/fantasy/update-battle-group/{groupId}";
    public static final String API_RESULT_BATTLE = "user/fantasy/battle-team-players/{battleId}";
    public static final String API_UPDATE_OPPONENTPLAYERS = "user/fantasy/join-clash-battle-player/{battleId}";
    public static final String API_KAPLAYERS = "user/fantasy/fetch-match-status/{matchId}";
    public static final String API_MATCHSTATUS = "user/fantasy/fetch-match-status-without-player/{matchId}";
    public static final String API_VERSION = "public/meta";
    public static final String API_MASTER = "user/master-data";
    public static final String API_BANNER = "user/banner";
    public static final String API_CANCEL_GROUP = "user/cancel-group/{id}";
    public static final String API_VERIFY_BENEFICIARY = "cashfree/add/beneficiary/verify/{id}";

    public static final String API_WITHDRAW_LIMIT = "user/fetch-withdrawal-status";
    public static final String API_ADD_BENEFICIARY = "cashfree/add/beneficiary";
    public static final String API_GET_BENEFICIARY = "cashfree/get/beneficiary";
    public static final String API_REMOVE_BENEFICIARY = "cashfree/remove/beneficiary/{beneId}";
    public static final String API_TRANSFER_AMOUNT_BANK = "cashfree/transfer/amount/{id}";

    public static final String API_TRUECALLER = "/auth/truecallerlogin";
    public static final String TRUECALLER_POLICY_URL = "https://truecaller.com";
    public static final String TRUECALLER_PRIVACY_URL = "https://truecaller.com";

    public static final String API_RAZORPAY_ADD_BENEFICIARY = "razorpay/add-contact";
    public static final String API_RAZORPAY_TRANSFER_AMOUNT = "razorpay/transfer-coins";

    public static final String API_MANUAL_WITHDRAW_HISTORY = "user/withdrawals";
    public static final String API_WITHDRAW_HISTORY = "user/withdrawal-history";
    public static final String API_WITHDRAW_REQUEST = "user/withdrawal/request";

    public static final String API_TRANSFER_COINS = "user/coins/transfer";
    public static final String API_TRANSACTION = "user/transaction";

    public static final String API_PAYMENT_DETAILS = "user/payment-details";
    public static final String API_PAYMENT_STATUS = "refresh-payment-details/{paymentId}";

    public static final String API_PAYU_CHECKSUM = "payumoney/getchecksum";
    public static final String LUDO_CREATE = "LUDO_CREATE";
    public static final String LUDO_ACCEPT = "LUDO_ACCEPT";
    public static final int FROM_FAVOURITE = 777;
    public static final String MATCH_LIVE = "MATCH_LIVE";
    public static final int FROM_NEOKRED = 8;
//    public static final int FROM_GAMECASE = 9;
    public static final String MATCH_TYPE = "MATCH_TYPE";
    public static final String API_CX_BANNER = "user/fantasy/fetch-banners";

    public static final String API_RACE_CONDITION = "racecondition/createContact";
    public static final String API_RACE_CONDITION_FINAL = "racecondition/transfer/{beneId}/{amount}/{otp}";
    public static final String API_INSTANT_PAY = "ipay/transfer/{beneId}/{amount}/{otp}/{latitude}/{longitude}";
//    public static final String API_GAMER_CASH_VERFIYUSER = "/user/gamercash/link-wallet";
//    public static final String API_FETCH_GAMER_CASH = "/user/gamercash/fethc-gc";
//    public static final String API_SWITCH_GAMER_CASH = "/user/gamercash/add-coins";

    public static final String API_GET_RUMMY_LIST = "/user/rummy/fetchCards";
    public static final String API_GET_RUMMY_REFRESH_TOKEN = "/user/rummy/fetchRefreshToken";
    public static final String API_GET_CALLBREAK_LIST = "/user/callbreak/fetchCards";
    public static final String API_GET_CALLBREAK_JOIN = "/user/callbreak/joinCard/{id}";


    public static String API_PAYU_UPI_CHECKSUM = "payumoney/generate/hash-checksum";
    public static final String API_PAYU_PAYMENT = "payumoney/verifychecksum";
    public static final String API_CASHFREE_CHECKHSUM = "cashfree/getchecksum";
    public static final String API_CASHFREE_PAYMENT = "cashfree/verify/getchecksum";
    public static final String API_RAZORPAY_ORDERID = "razorpay/create-order";
    public static final String API_RAZORPAY_PAYMENT = "razorpay/payment-processed";
    public static final String API_PAYKUN_ORDERID = "paykun/start-payment";
    public static final String API_PAYKUN_PAYMENT = "paykun/payment-processed/{paymentId}/{orderId}";
    public static final String API_PAYTM_CHECKSUM = "getchecksum";
    public static final String API_PAYTM_PAYMENT = "savePayment";

    public static final String API_USERS_TOKEN = "user/contest-opponent/token/{contestId}";
    public static final String API_CONTEST = "user/contest";
    public static final String API_JOIN_CONTEST = "user/join/contest/{contest_id}";
    public static final String API_CANCEL_CONTEST = "user/cancel/contest/{contest_id}";
    public static final String API_ROOMCODE = "user/update/contest/{contest_id}";
    public static final String API_UPDATE_RESULT = "user/contest/upload-result/{contest_id}";
    public static final String API_CONTEST_REASON = "user/contest/result-not-update/reason/{contest_id}";
    public static final String API_BUDDY_OPPONENT = "user/recent-opponents";
    public static final String API_NOTIFY_OPPONENT = "user/send-notification-opponents";

    public static final String API_FAQ = "user/faqs";
    public static final String API_CATEGORY_FAQ = "user/categories-faqs";

    //Ludo Universe
    public static final String API_LU_CONTEST = "user/ludo-universe/fetch-challanges";
    public static final String API_LU_MY_CONTEST = "user/ludo-universe/fetch-self-challanges";
    public static final String API_LU_ADD_CONTEST = "user/ludo-universe/create-challange";
    public static final String API_LU_JOIN_CONTEST = "user/ludo-universe/accept-challange/{contest_id}";
    public static final String API_LU_CANCEL_CONTEST = "user/ludo-universe/cancel-challange/{contest_id}";
    public static final String API_LU_BUDDY_OPPONENT = "user/ludo-universe/recent-opponents";
    public static final String API_LU_STATUS = "user/ludo-universe/join-contest-status/{contest_id}";
    public static final String API_LU_MODE = "user/ludo-universe/fetch-ludoconfig/{bannerType}";
    public static final String API_LU_OVERALLEADERBOARD = "user/ludo-universe/fetch-leaderboard";
    public static final String API_DROID_DO_OVERALLEADERBOARD = "user/gamekite/tournament/all-leaderboard";
    public static final String API_WS_OVERALLEADERBOARD = "/user/word-search/final-leaderboard";
    public static final String API_LUDO_TOURNAMENT = "user/ludo/tournament/tournament/leaderboard";
    public static final String API_CALL_BREAK = "/user/callbreak/leaderboard";
    public static final String API_RUMMY = "/user/rummy/leaderboard";

    public static final String API_ADD_CASHFREE_BENEFICIARY = "cashfree/add/beneficiary";
    public static final String TEXT_KEY_TYPE = "type";

    public static final String API_FIREBASE = "user/firebase";
    public static final String API_MATE_DATA = "user-detail/mobile/{id}";

    public static final String API_REFER_HISTORY = "user/reference";
    public static final String API_BOOKMARK_FACT = "user/bookmark/Fact/{id}";
    public static final String API_BOOKMARK_FACT_LIST = "user/bookmarked-facts";
    public static final String API_LIKED_FACTS_LIST = "user/liked-facts";
    public static final String API_TRENDING_FACTS = "user/trending/fact";
    public static final String API_FACTS = "user/facts";
    public static final String API_FACT_DETAIL = "user/fact/{id}";
    public static final String API_LIKED_FACTS = "user/like/Fact/{id}";

    public static final String API_CHECK_AADHAR = "user/check-aadhaar-exists";
    public static final String API_INVOICE = "user/invoice-pdf/{id}";
    public static final String API_APPLY_COUPON = "user/check-coupon/{coupon}";

    public static final String API_PROFILE = "profile";
    public static final String API_UPDATE_PROFILE = "user/profile";
    public static final String API_UPLOAD_IMAGE = "upload";

    public static final String API_TRENDING_QUIZ = "user/trending-quiz";
    public static final String API_QUIZ_LIST = "user/quiz/{id}";
    public static final String API_QUIZ_DETAIL = "user/quiz-detail/{id}";
    public static final String API_QUIZ_QUESTION = "user/quiz-ques/{id}";
    public static final String API_START_QUIZ = "user/start-quiz";
    public static final String API_SUBMIT_QUIZ = "user/submit-quiz";
    public static final String API_QUIZ_PARTICIPANT = "user/quiz-participants/{id}";
    public static final String API_QUIZ_LEADERBOARD_ALL = "user/leaderboard";
    public static final String API_QUIZ_LEADERBOARD_MONTHLY = "user/leaderboard/month";
    public static final String API_QUIZ_LEADERBOARD = "user/leaderboard/{id}";

    public static final String API_SCRATCH_CARDS = "user/fetch-scratch-cards";
    public static final String API_SCRATCHED_CARD = "/user/use-scratchCard/{scratched}";
    public static final String API_APPLY_VOUCHER = "/user/use-voucherCard";
    public static final String API_HTH_MATHCES = "/user/fantasy/fetch-fantasy-matches";
    public static final String API_HTH_LEGUES = "user/fantasy/fetch-clash-matches";
    public static final String API_HTH_CANCELBATTLE = "user/fantasy/cancel-battle-group/{id}";
    public static final String API_GAME_ID = "user/game";
    public static final String API_GAME_CATEGORY = "user/game/{id}";
    public static final String API_CR_LEAGUE = "user/game-league/{id}";
    public static final String API_LEAGUE_PARTICIPANT = "user/league/{id}";
    public static final String API_JOIN_LEAGUE = "user/attempt-league/{id}";
    public static final String API_CREATE_TEAM = "user/attempt-league/{id}";
    public static final String API_MY_LEAGUE = "user/my-league/{id}";
    public static final String API_MY_TEAM = "user/game-league/my-team/{id}";
    public static final String API_TEAM_ALL = "user/game-league/teams/{id}";
    public static final String API_GAME_LEADERBOARD_ALL = "user/game/leaderboard/{id}";
    public static final String API_GAME_LEADERBOARD_MONTHLY = "user/game/month-leaderboard/{id}";
    public static final String API_GAME_LEADERBOARD = "user/league/leaderboard/{id}";
    public static final String API_GAME_TEAM_LEADERBOARD = "user/league/team-leaderboard/{id}";
    public static final String API_CONTEST_LEADERBOARD = "user/contest-leaderboard";

    public static final String API_JOIN_SUBSTITUTE_GROUP = "user/change/joined-group";
    public static final String API_FB_GROUP_PARTICIPANT = "user/fantasy-leaderboard";
    public static final String API_OVERALLLEADBOARD = "user/fantasy-overall-leaderboard";
    public static final String API_HTH_OVERALLLEADBOARD = "user/fantasy/clash-battle-leaderboard";

    public static final String API_AADHAR_GET_CAPTCHA = "getCaptcha";
    public static final String API_AADHAR_GET_OTP = "enterAadhaar";
    public static final String API_AADHAR_VERIFY_OTP = "enterOtp";
    public static final String API_AADHAR_DETAILS = "fetchKYCData";
    public static final String API_AADHAR_VERIFY_OTP_SUREPASS_SERVER = "user/verify-aadhaar-otp";
    public static final String API_QUIZ_TIME = "user/start-quiz-timer/{id}";
    public static final String APP_DIRECTORY_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + ".khiladiAddaUser";
    public static final String IMAGE_PATH = File.separator + "image.";
    public static final String AADHAR_IMAGE_PATH = File.separator + "image_";
    public static final int RC_ASK_PERMISSIONS_STORAGE = 101;
    public static final int RC_ASK_PERMISSIONS_CAMERA = 102;
    public static final int RC_ASK_PERMISSIONS_GPS = 103;
    public static final int RC_ASK_PERMISSIONS_MSG = 105;
    public static final int REQUEST_GALLERY = 202;

    /**
     * Word Search End Points
     **/
    public static final String API_WORD_SEARCH_TRENDING_QUIZ = "user/word-search/trending-quiz";
    public static final String API_WORD_SEARCH_START_QUIZ = "user/word-search/start-quiz/{quizId}";
    public static final String API_WORD_SEARCH_CATEGORIES_QUIZZES = "user/word-search/fetch/category/quizzes/{quizId}";
    public static final String API_WORD_SEARCH_QUIZ_PARTICIPANTS = "user/word-search/quiz/participants/list/{quizId}";
    public static final String API_WORD_SEARCH_MY_QUZZIES = "user/word-search/my-quizzes";
    public static final String API_WORD_SEARCH_LIVE_LEADERBOARD = "user/word-search/live-leaderboard/{quizId}";

    /**
     * LUDO TOURNAMENT
     *
     **/
    public static final String API_LUDO_ALL_TOURNAMENT = "user/ludo/tournament/fetch";
    public static final String API_LUDO_JOIN_TOURNAMENT = "user/ludo/tournament/join-tournament/{id}";
    public static final String API_LUDO_PAST_TOURNAMENT = "user/ludo/tournament/my/past-tournaments";
    public static final String API_LUDO_LIVE_TOURNAMENT = "user/ludo/tournament/my/live-tournaments";
    public static final String API_LUDO_UPCOMING_TOURNAMENT = "user/ludo/tournament/my/upcoming-tournaments";
    public static final String API_LUDO_TOURNAMENT_ROUNDS = "user/ludo/tournament/tournament/room-details/{id}";
    public static final String API_LUDO_TOURNAMENT_PAST_ROUNDS = "user/ludo/tournament/tournament/past-rooms-details/{id}";


    public static final int FROM_WON = 1;
    public static final int FROM_LOST = 2;
    public static final int PAGE_SIZE = 20;
    public static final int FROM_REGISTRATION_OTP = 703;
    public static final int FROM_LUDO_LIST = 705;
    public static final int LEADERBOARD_LISTING_ALL = 0;
    public static final int LEADERBOARD_FANBATTLE_LISTING_ALL = 0;
    public static final int LEADERBOARD_LISTING_MONTHLY = 2;
    public static final int LEADERBOARD_LISTING_DAILY = 1;
    public static final int LEADERBOARD_LISTING_WEEKLY = 3;
    public static final int LEADERBOARD_FANBATTLE_LISTING_MONTHLY = 1;
    public static final int LEADERBOARD_FANBATTLE_LISTING_DAILY = 3;
    public static final int LEADERBOARD_FANBATTLE_LISTING_WEEKLY = 2;
    public static final String LEADERBOARD_HTH_LISTING_MONTHLY = "monthly";
    public static final int LEADERBOARD_TYPE_QUIZ = 1;
    public static final int LEADERBOARD_TYPE_GAME = 2;
    public static final int LEADERBOARD_TYPE_LUDO = 3;
    public static final int LEADERBOARD_TYPE_SNAKE = 4;
    public static final int LEADERBOARD_TYPE_FAN_BATTLE = 5;
    public static final int LEADERBOARD_TYPE_HTH_BATTLE = 6;
    public static final int LEADERBOARD_TYPE_LUDOADDA = 7;
    public static final int LEADERBOARD_TYPE_DROID_DO = 8;
    public static final int LEADERBOARD_TYPE_WS = 9;
    public static final int LEADERBOARD_TYPE_LUDO_TOURNAMENT = 10;
    public static final int LEADERBOARD_TYPE_COURTPIECE = 11;
    public static final int LEADERBOARD_TYPE_RUMMY = 12;
    public static final int LEADERBOARD_FROM_LEADERBOARD = 1;
    public static final int REQUEST_ADD_WALLET = 5001;
    public static final int FROM_LOGIN = 1;
    public static final int LEAGUE_JOIN = 901;
    public static final int LEAGUE_CREATE = 902;
    public static final String LUDO_AUDIO = "LUDO_AUDIO";
    public static final String FROM_WITHDRAW = "DEPOSIT";
    public static final String FROM_WALLET = "WALLET";
    public static final String UNDER_MAINTENANCE = "UNDER_MAINTENANCE";
    public static final String USER_BLOCKED = "USER_BLOCKED";
    public static final String USER_LOGOUT = "USER_LOGOUT";
    public static final String IS_DATA_CHANGE = "IS_DATA_CHANGED";
    public static final String IS_QUIZ_PLAYED = "IS_QUIZ_PLAYED";
    public static final String SENDER_ID = "SENDER_ID";
    public static final String RECEIVER_ID = "RECEIVER_ID";
    public static final String SENDER_NAME = "SENDER_NAME";
    public static final String RECEIVER_CHATID = "RECEIVER_CHATID";
    public static final String QUIZ_TYPE = "QUIZ_TYPE";
    public static final String VERSION_UPDATE = "VERSION_UPDATE";
    public static final String VERSION = "VERSION";
    public static final String VERSION_DESC = "VERSION_DESC";
    public static final String VERSION_LINK = "VERSION_LINK";
    public static final String VERSION_SIZE = "VERSION_SIZE";
    public static final int FROM_ERROR = 1;
    public static final int FROM_CANCEL = 3;
    public static final String CATEGORY = "CATEGORY";
    public static final String CONTEST_TYPE = "CONTEST_TYPE";
    public static final int FROM_ADD = 1;
    public static final int FROM_ACCEPT = 3;
    public static final int FROM_LUDO_RESULT = 4;
    public static final String TYPE_ALL = "all";
    public static final String TYPE_MONTHLY = "monthly";
    public static final String TYPE_WEEKLY = "weekly";
    public static final String TYPE_DAILY = "daily";

    public static final String LUDO_BUDDY = "LUDO_BUDDY";
    public static final String LEAGUE_CREATE_JOIN_HELP = "LEAGUE_CREATE_JOIN_HELP";

    public static final int FROM_SUCCESS = 0;
    public static final int FROM_MAINTENANCE = 1;
    public static final int FROM_UPDATE = 2;
    public static final int FROM_BLOCKED = 3;
    public static final int FROM_FORCED_LOGOUT = 10;
    public static final String FROM_TRANSACTION = "TRANSACTION";
    public static final String PAYU_KEY = "KKmZRoSD";
    public static final String PAYU_ID = "QIflppzrFU";

    public static final int FROM_FANBATTLE = 0;
    public static final int FROM_FANBATTLE_LIVE = 1;
    public static final int FROM_FANBATTLE_PAST = 2;
    public static final int FROM_FANBATTLE_UPCOMING = 3;
    public static final int REVIEWPending = 4;
    public static final int LIVEMATCH = 3;
    public static final int COMPLETED = 5;
    public static final int FROM_LOGOUT = 666;
    public static final String GROUP_JOINED = "GROUP_JOINED";
    public static final String REVIEW = "REVIEW";
    public static final String CASHFREE_TEST = "TEST";
    public static final String CASHFREE_PROD = "PROD";
    public static final int FROM_CASHFREE_GPAY = 11;
    public static final int FROM_CASHFREE_PPAY = 12;
    public static final int FROM_CASHFREE_APAY = 13;
    public static final int FROM_CASHFREE_UPI = 14;
    public static final String FB_VIDEO_SEEN = "FB_VIDEO_SEEN";
    public static final String VPA = "vpa";
    public static final String BANK_ACCOUNT = "bank_account";
    public static final String INR = "INR";
    public static final String COMPANY_NAME = "Techbeliever Technologies Pvt Ltd";
    public static final String APP_NAME = "KhiladiAdda";
    public static final int FROM_PAYKUN = 5;
    public static final String FROM_FB_GROUP = "FAN_BATTLE_GROUP";
    public static final String FROM_FB_BATTLE = "FB_BATTLE";
    public static final String FROM_FB_MATCH = "FB_MATCH";
    public static final int FROM_CREATE_LUDO = 3;
    public static final String FROM_TYPE = "FROM_TYPE";
    public static final String LUDO_SECURE_MSG = "LUDO_SECURE_MSG";
    public static final String FB_CHANGE_GROUP_MSG = "FB_CHANGE_GROUP_MSG";
    public static final String PLAYER_ID = "PLAYER_ID";
    public static final String FB_CLASSIC_VIEWED = "FB_CLASSIC_VIEWED";
    public static final String FB_CLASSIC = "CLASSIC";
    public static final String FB_RUMBLE = "REVERSE";
    public static final String FB_HEAD = "HeadToHead";
    public static final String FB_RUMBLE_VIEWED = "FB_RUMBLE_VIEWED";
    public static final String FB_CLASSIC_WORK = "FB_CLASSIC_WORK";

    public static final String FB_HEAD_VIEWED = "FB_HEAD_VIEWED";

    public static final String FROM_PAN = "PAN";
    public static final String FROM_AADHAR = "AADHAR";
    //    public static final String AADHAR_CLIENT_ID = "TECH7966";
    public static final String AADHAR_CLIENT_ID = "TECH3772";
    //    public static final String AADHAR_API_KEY= "56645";
    public static final String AADHAR_API_KEY = "56823654656358";
    //    public static final String AADHAR_SALT= "145648r";
    public static final String AADHAR_SALT = "4vcf45fgndvc34y6f";
    public static final String AADHAR_SELF = "SELF";
    public static final String AADHAR_DEFAULT = "DEFAULT";
    public static final String AADHAR_TRIAL = "TRIAL";
    public static final String TEXT_INTERNET_ISSUE = "Please check your internet connection. You can also connect to our support.";
    public static final String APPSKEY = "H4n2Z9FGJbEU7F2EAuCVvQ";
    public static final String MOENGAGEAPPSKEY = "T929VRN0YM81Z3YD35CWVZRU";
    public static final int PLAYER_BATSMAN = 1;
    public static final int PLAYER_BOWLER = 2;
    public static final int PLAYER_WICKET_KEEPER = 4;
    public static final int PLAYER_ALL_ROUNDER = 3;
    public static final int FROM_HELP = 500;
    public static final String FIREBASE_CHAT_EMAIL = "test_chat_khiladi@gmail.com";
    public static final String FIREBASE_CHAT_PWD = "TB2019KA";
    public static final String IS_FB_ENABLED = "IS_FB_ENABLED";
    public static final String IS_GMAIL_ENABLED = "IS_GMAIL_ENABLED";
    public static final String IS_PAYTMWALLET_ENABLED = "IS_PAYTMWALLET_ENABLED";
    public static final int WITHDRAW_VERIFICATION = 1;
    public static final String INSUFFICIENT_WALLET_RECHARGE = "Your wallet balance is insufficient. Please recharge your wallet to play and earn.";
    public static final String TEXT_OPPONENT_COMBO = "Opponent’s Combo";
    public static final String TEXT_YOUR_COMBO = "Your Combo";

    public static int REQUEST_NOTIFICATION = 902;
    public static final int FROM_NOTIFICATION = 901;

    public static final int FROM_PAYTM = 1;
    public static final int FROM_CASHFREE = 2;
    public static final int FROM_PAYU = 3;
    public static final int FROM_RAZORPAY = 4;

    //Paytm
    public static final String PaytmStagingCallbackURL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
    public static final String PaytmProductionCallbackURL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
    public static final String PaytmStagingMID = "V01meWNUNTQwMzQ3NDU5NjIwOTU=";
    public static final String PaytmProductionMID = "c2NuWEJXMTcxMjEwMDY3MDk0MTk=";
    //Cashfree
    public static final String CASHFREE_STAGING_APP_ID = "MTI0ODliMWIyZDIxYzUwMGMzYTk4OGJhNTk4NDIx";
    public static final String CASHFREE_PRODUCTION_APP_ID = "MzgxOTkzMDc3NzY5MWY1YzhmZGEwYzNmYTk5MTgz";


    public static final String TXN_FAILED = "FAILED";
    public static final String TXN_SUCCESS = "SUCCESS";
    public static final String TXN_PENDING = "PENDING";
    public static final String STATUS = "STATUS";
    public static final String BANKNAME = "BANKNAME";
    public static final String TXN_AMOUNT = "TXNAMOUNT";
    public static final String TXN_DATE = "TXNDATE";
    public static final String TXN_ID = "TXNID";
    public static final String PAYMENT_MODE = "PAYMENTMODE";
    public static final String BANK_TXN_ID = "BANKTXNID";
    public static final String CURRENCY = "CURRENCY";
    public static final String GATEWAY_NAME = "GATEWAYNAME";

    public static final String FROM = "FROM";
    public static final String FROM_PLAYER = "FROM_PLAYER";
    public static final int FROM_PLAY = 5;
    public static final String LIVEMATCHID = "LIVEMATCHID";
    public static final String FROM_GMAIL = "GMAIL";
    public static final String FROM_FB = "FB";
    public static final String ID = "ID";
    public static final String ROOM_ID = "ROOM_ID";
    public static final String FROM_FORGOT_PASSWORD = "FORGOT_PASSWORD";
    public static final String USER_ID = "USERID";
    public static final String EXTRA_SELECTED = "extra_selected";
    public static final String PAST = "PAST";
    public static final String LIVE = "LIVE";
    public static final String UPCOMING = "UPCOMING";
    public static final String EXTRA_CURRENT_INDEX = "extra_current_index";
    public static final String EXTRA_TOTAL = "extra_total";
    public static final String EXTRA_QUIZ_ID = "extra_quiz_id";
    public static final String TERMS = "TERMS_CONDITIONS";
    public static final String CONTACT = "CONTACT";
    public static final String ABOUT = "ABOUT";
    public static final String CANCELLATION = "CANCELLATION";
    public static final String PRIVACY = "PRIVACY";
    public static final String LEGALITY = "LEGALITY";
    public static final String FROM_UPDATE_MOBILE = "UPDATE MOBILE";
    public static final String ANDORID = "ANDROID";
    public static final String UUID = "UUID";

    public static final String FROM_MAIN = "FROM_MAIN";
    public static final String FROM_MYLEAGUE = "MYLEAGUE";
    public static final String DEPOSIT = "DEPOSIT";
    public static final String LEAGUE = "LEAGUE";
    public static final String FROM_VIEW_FF_CLASH = "FF_CLASH";
    public static final String FROM_VIEW_FREEFIRE = "FREEFIRE";
    public static final String FROM_VIEW_CLASHROYALE = "CLASHROYALE";
    public static final String FROM_VIEW_LUDO = "LUDO";
    public static final String FROM_VIEW_LUDO_UNIVERSE = "LUDO_UNIVERSE";
    public static final String UPI = "UPI";
    public static final String PATYM = "PAYTM";
    public static final String PATYMWALLTERUPI = ".wallet@paytm";
    public static final String PATYMUPI = "PAYTMUPI";
    public static final String BANK_TRANSFER = "BANKTRANSFER";
    public static final String AMAZON_PAY = "AMAZONPAY";
    public static final String FROM_HOW_JOINING = "HOWTOJOIN";
    public static final String DATA_QUIZ = "DATA_QUIZ";
    public static final String QUIZ_DETAILS = "QUIZ_DETAILS";
    public static final String QUIZ_QUESTION = "QUIZ_QUESTION";
    public static final String LUDO_VIDEO_SEEN = "LUDO_VIDEO_SEEN";
    public static final String SNAKE_VIDEO_SEEN = "SNAKE_VIDEO_SEEN";
    public static final String LUDO_WON = "LUDO_WON";
    public static final String LUDO_ERROR = "LUDO_ERROR";
    public static final String DATA_QUESTION = "DATA_QUIZ_QUESTION";
    public static final String TRENDING = "TRENDING";
    public static final String CHAT_ID = "CHAT_ID";
    public static final String TRUE = "true";
    public static String MobileNumber = "mobile_number";
    public static String DATA = "data";

    public static String MATCH_DATA = "match_data";
    public static String BATTLE_DATA = "battle_data";
    public static String BATTLE_DATAOPP = "battle_data_opp";
    public static String NEWESTFILTER = "NEWESTFILTER";
    public static String HIGHFILTER = "HIGHFILTER";
    public static String LOWFILTER = "LOWFILTER";
    public static String MYBATTLES = "MYBATTLES";
    public static String Captain = "Captain";
    public static String Opponent = "Opponent";

    public static String EXTRA_TYPE = "type";
    public static String BATTLE_CATEGORY = "BATTLE_CATEGORY";
    public static final String FROM_QUIZ_QUESTION_TEXT = "QUIZ_QUESTION_TEXT";
    public static final String FROM_QUIZ_QUESTION_IMAGE = "QUIZ_QUESTION_IMAGE";
    public static final String FROM_PICTURE = "PICTURE QUIZ";
    public static final String FROM_GAMING = "GAMING QUIZ";
    public static final String FROM_WEB = "WEB QUIZ";
    public static final String FROM_LOGO = "LOGO QUIZ";
    public static final String FROM_SPORTS = "SPORTS QUIZ";
    public static final String FROM_TECHNOLOGY = "TECHNOLOGY QUIZ";
    public static final String FROM_SCIENCE = "SCIENCE QUIZ";
    public static final String FROM_MATH = "MATH QUIZ";
    public static final String FROM_GK = "GK QUIZ";
    public static final String FROM_MOVIES = "MOVIES QUIZ";
    public static final String FROM_VIEW_QUIZ = "QUIZ";
    public static final String SOLO = "SOLO";
    public static final String DUO = "DUO";
    public static final String SQUAD = "SQUAD";
    public static final String FROM_VIEW_TDM = "PUBG";
    public static final String PUBG_ID = "PUBG_ID";
    public static final String PUBG_SOLO = "PUBG_SOLO";
    public static final String PUBG_DUO = "PUBG_DUO";
    public static final String PUBG_SQUAD = "PUBG_SQUAD";
    public static final String FROM_VIEW_BGMI = "PUBG_LITE";
    public static final String PUBG_LITE_ID = "PUBG_LITE_ID";
    public static final String PUBG_LITE_SOLO = "PUBG_LITE_SOLO";
    public static final String PUBG_LITE_DUO = "PUBG_LITE_DUO";
    public static final String PUBG_LITE_SQUAD = "PUBG_LITE_SQUAD";
    public static final String CALLOFDUTY = "CALL OF DUTY";
    public static final String FROM_VIEW_CALLOFDUTY = "CALLOFDUTY";
    public static final String CALL_DUTY_ID = "CALL_DUTY_ID";
    public static final String CALL_DUTY_SOLO = "CALL_DUTY_SOLO";
    public static final String CALL_DUTY_DUO = "CALL_DUTY_DUO";
    public static final String CALL_DUTY_SQUAD = "CALL_DUTY_SQUAD";
    public static final String FREEFIRE = "FREE FIRE";
    public static final String FREEFIRE_ID = "FREEFIRE_ID";
    public static final String FREEFIRE_SOLO = "FREEFIRE_SOLO";
    public static final String FREEFIRE_DUO = "FREEFIRE_DUO";
    public static final String FREEFIRE_SQUAD = "FREEFIRE_SQUAD";
    public static final String CLASHROYALE = "CLASH ROYALE";
    public static final String CLASHROYALE_ID = "CLASHROYALE_ID";
    public static final String LUDO_ID = "LUDO_ID";
    public static final String LUDO_UNIVERSE_ID = "LUDO_UNIVERSE_ID";
    public static final String LUDO = "LUDO";
    public static final String LUDO_CONTEST = "LUDO_CONTEST";
    public static final String SNAKE_LADDER = "SNAKE_LADDER";
    public static final String SNAKE_LADDER_ID = "SNAKE_LADDER_ID";
    public static final String FROM_VIEW_PUBG_GLOBAL = "PUBG GLOBAL";
    public static final String PUBG_GLOBAL_ID = "PUBG_GLOBAL_ID";
    public static final String PUBG_GLOBAL_SOLO = "PUBG_GLOBAL_SOLO";
    public static final String PUBG_GLOBAL_DUO = "PUBG_GLOBAL_DUO";
    public static final String PUBG_GLOBAL_SQUAD = "PUBG_GLOBAL_SQUAD";

    public static final String FROM_VIEW_PREMIUM_ESPORTS = "PREMIUM ESPORTS";
    public static final String PREMIUM_ESPORTS_ID = "PREMIUM_ESPORTS_ID";
    public static final String PREMIUM_ESPORTS_SOLO = "PREMIUM_ESPORTS_SOLO";
    public static final String PREMIUM_ESPORTS_DUO = "PREMIUM_ESPORTS_DUO";
    public static final String PREMIUM_ESPORTS_SQUAD = "PREMIUM_ESPORTS_SQUAD";

    public static final String FROM_VIEW_PUBG_NEWSTATE = "PUBG NEWSTATE";
    public static final String PUBG_NEWSTATE_ID = "PUBG_NEWSTATE_ID";
    public static final String PUBG_NEWSTATE_SOLO = "PUBG_NEWSTATE_SOLO";
    public static final String PUBG_NEWSTATE_DUO = "PUBG_NEWSTATE_DUO";
    public static final String PUBG_NEWSTATE_SQUAD = "PUBG_NEWSTATE_SQUAD";

    public static final String FF_CLASH = "FF_CLASH";
    public static final String FF_CLASH_ID = "FF_CLASH_ID";
    public static final String FF_CLASH_SOLO = "FF_CLASH_SOLO";
    public static final String FF_CLASH_DUO = "FF_CLASH_DUO";
    public static final String FF_CLASH_SQUAD = "FF_CLASH_SQUAD";
    public static final String FAN_BATTLE = "FanBattle";
    public static final String FAN_BATTLE_GAME_ID = "FAN_BATTLE";
    public static final String CLASHOFFANS = "HTH";
    public static final String HTHMATCHES = "HTH_MATCHES";
    public static final String HTHREFRSH = "HTHREFRSH";
    public static final String HTHLIVEREFRSH = "HTHLIVEREFRSH";
    public static final String HTHPASTREFRSH = "HTHPASTREFRSH";
    public static final String CLASH_X = "CLASH X";
    public static final String CLASH_X_GAME_ID = "CLASH_X";

    public static final String FF_MAX = "FREEFIRE MAX";
    public static final String FF_MAX_ID = "FF_MAX_ID";
    public static final String FF_MAX_SOLO = "FF_MAX_SOLO";
    public static final String FF_MAX_DUO = "FF_MAX_DUO";
    public static final String FF_MAX_SQUAD = "FF_MAX_SQUAD";
    public static final String FROM_VIEW_FF_MAX = "FF_MAX";

    public static final String LUDO_TOURNAMENT_ID = "LUDO_TOURNAMENT_ID";

    public static final String RUMMY_ID = "LUDO_TOURNAMENT_ID";




    public static final int TYPE_LUDO = 1;
    public static final int TYPE_SNAKE_LADDER = 2;

    public static final String WEBSITE_URL = "https://www.khiladiadda.com";

    public static final String RULE_GAME = "Minimum 30 level players are allowed to participate in KhiladiAdda matches, if low-level players found they will be kicked immediately from the match.\n" +
            "\nUnregistered Players are not allowed, inviting unregistered players leads to Penalty or No rewards.\n" +
            "\n Record Your Gameplay, You can Be Asked To Provide POV/Recordings.\n" +
            "\nThe maximum headshot percentage of every player should not be more than 38%, if high percentage found they will be kicked immediately from the match.\n" +
            "\nUsing Hacks, Griefing, Teaming, and Rules Violation in KhiladiAdda matches are strictly prohibited. Any such violation will lead to no rewards claim or even your account can be ban.\n" +
            "\nBattleGround India Maps must be downloaded by the player before the Match Starts.\n" +
            "\nBRDM Not allowed.\n" +
            "\nIn Duo/Squad Matches, only Captain has to pay the Fees.\n" +
            "\nAlways allow Sound Notification of KhiladiAdda App in your device, so you will remember your match on time.\n" +
            "\nRoom ID and Password will be shared at the exact start time of the match and the match will start within in 15 min. Before the match starts,\n" +
            "If it is late, then we will send you a notification about it,but please confirm on WhatsApp Help support once.\n" +
            "\nDon't share match Room ID and Password to anyone. If we found it happened, we will take strict action against you.\n" +
            "\nMatch will start at the exact time if the time will change\n" +
            "\nWe will inform you through notification. Room ID and Password will be shown in the Notification and My leagues live section, once it is updated by KhiladiAdda Team.\n" +
            "\nJoin the Match as soon as possible, because if you don't enter on time, we can't refund your coins.\n" +
            "\nOnce you Joined the Custom Room, don't keep changing your position otherwise, Host will kick you out from the room.\n" +
            "\nAbusing in Custom Room Chats and anyhow on KhiladiAdda Platform will result in Kick Out on that particular matches.\n" +
            "\nIf you think any cheating is going on, Please Capture some Proof and Complain us about it, KhiladiAdda never tolerate cheating.\n" +
            "\nContact us through WhatsApp on WhatsApp help Support using the \"Contact Us\" option on the home screen for any help or support related to BattleGround India matches on KhiladiAdda.";

    public static final String RULE_TDM = "Minimum 30 level players are allowed to participate in KhiladiAdda matches, if low-level players found they will be kicked immediately from the match.\n" +
            "\nUnregistered Players are not allowed, inviting unregistered players leads to Penalty or No rewards.\n" +
            "\n Record Your Gameplay, You can Be Asked To Provide POV/Recordings.\n" +
            "\nThe maximum headshot percentage of every player should not be more than 38%, if high percentage found they will be kicked immediately from the match.\n" +
            "\nUsing Hacks, Griefing, Teaming, and Rules Violation in KhiladiAdda matches are strictly prohibited. Any such violation will lead to no rewards claim or even your account can be ban.\n" +
            "\nBattleGround India Maps must be downloaded by the player before the Match Starts.\n" +
            "\nIn Duo/Squad Matches, only Captain has to pay the Fees.\n" +
            "\nAlways allow Sound Notification of Khiladi Adda App in your device, so you will remember your match on time.\n" +
            "\nRoom ID and Password will be shared before 5 minutes of the match start time, before the match starts.\n" +
            "\nIf it is late, then we will send you a notification about it,but please confirm on WhatsApp Help support once.\n" +
            "\nDon't share match Room ID and Password to anyone, If we found it happened, we will take strict action against you.\n" +
            "\nMatch will start at the exact time if the time will change we will inform you through notification.\n" +
            "\nRoom ID and Password will be shown in the Notification and My leagues live section, once it is updated by KhiladiAdda Team.\n" +
            "\nJoin the Match as soon as possible, because if you don't enter on time, we can't refund your coins.\n" +
            "\nOnce you Joined the Custom Room, don't keep changing your position otherwise, the Host will kick you out from the room.\n" +
            "\nAbusing in Custom Room Chats and anyhow on KhiladiAdda Platform will result in Kick Out on that particular matches.\n" +
            "\nIf you think any cheating is going on, Please Capture some Proof and Complain us about it, KhiladiAdda never tolerate cheating.\n" +
            "\nContact us through WhatsApp on WhatsApp help Support using the \"Contact Us\" option on the home screen for any help or support related to BattleGround India matches on KhiladiAdda.";

    public static final String RULE_QUIZ = "\n\n1. Ranks will be calculated according to the highest score and the fastest time span .\n\n" + "2. Please check your updated rank at the end of the contest.\n\n" + "3. All prizes will be credited to your Khiladi Adda wallet once the quiz ends.\n\n" + "4. For all the queries , you may contact us via email support and the Whatsapp support in our help center.\n\n" + "5. If number of entries are not filled by the quiz end time, prize pool will automatically shrink according to the number of entries.\n\n" + "6. You can take maximum 30 seconds to answer a question after that question automatically skip and move to next question.\n\n" + "7. MAX 3 Attempts - You can play one quiz to maximum three times. Coins will be deducted only once.\n\n" + "8. Result of last attempt will be considered as final.\n\n" + "9. Please play all quizzes with your full honesty if anyone found guilty his/her account may be terminated.";

    public static final String RULE_CALL_DUTY = "The room ID and password for the game will be provided 15min before the start time of the contest.\n" + "\n" + "If you are unable to join the custom tournament created on the Call of Duty platform by the start time, the joining fee will not be refunded.\n" + "\n" + "If you join the tournament on the Call of Duty platform without joining the contest on Khiladi Adda, you will be kicked out of the tournament by our moderator and your account will be banned from Khiladi Adda.\n" + "\n" + "Make sure not to share the RoomId and password with anyone who has not joined the contest on Khiladi Adda. This will result in your account being banned and your winnings being frozen.\n" + "\n" + "Prize pool will automatically shrink according to no entries ";

    public static final String RULE_FREEFIRE = "Minimum 25 level players are allowed to participate in KhiladiAdda matches, if low-level players found they will be kicked immediately from the match.\n" +
            "\nUnregistered Players are not allowed, inviting unregistered players leads to Penalty or No rewards\n" +
            "\nThe maximum headshot percentage of every player should not be more than 60% in life time mode, if high percentage found they will be kicked immediately from the match.\n" +
            "\n Record Your Gameplay, You can Be Asked To Provide POV/Recordings.\n" +
            "\nUsing Hacks, Griefing, Teaming, and Rules Violation in KhiladiAddabmatches are strictly prohibited. Any such violation will lead to no rewards claim or even your account can be ban.\n" +
            "\nFreefire Maps must be Downloaded by the player before the Match Starts.\n" +
            "\nDouble vector not allowed.\n" +
            "\nIn Duo/Squad Matches, only Captain has to pay the Fees.\n" +
            "\nAlways allow Sound Notification of Khiladi Adda App in your device, so you will remember your match on time. \n" +
            "\nRoom ID and Password will be shared at the exact start time of the match and  the match will start within 10 min, before the match starts.\n" +
            "\nIf it is late then we will send you a notification about it, but please confirm on WhatsApp Help support once.\n" +
            "\nDon't share match Room ID and Password to anyone, If we found it happened, we will take strict action against you.\n" +
            "\nMatch will start at the exact time if the time will change we will inform you through notification.\n" +
            "\nRoom ID and Password will be shown in the Notification and My leagues live section, once it is updated by KhiladiAdda Team.\n" +
            "\nJoin the Match as soon as possible, because if you don't enter on time, we can't refund your coins.\n" +
            "\nOnce you Joined the Custom Room, don't keep changing your position otherwise Host will kick you out from the room.\n" +
            "\nAbusing in Custom Room Chats and anyhow on KhiladiAdda Platform will result in Kick Out on that particular matches.\n" +
            "\nIf you think any cheating is going on, Please Capture some Proof and Complain us about it, KhiladiAdda never tolerate cheating.\n" +
            "\nContact us through WhatsApp on WhatsApp help Support using the \"Contact Us\" option on the home screen for any help or support related to Freefire matches on KhiladiAdda.";

    public static final String RULE_FF_CLASH = "Minimum 30 level players are allowed to participate in KhiladiAdda matches, if low-level players found they will be kicked immediately from the match.\n" +
            "\nUnregistered Players are not allowed, inviting unregistered players leads to Penalty or No rewards\n" +
            "\n Record Your Gameplay, You can Be Asked To Provide POV/Recordings.\n" +
            "\nThe maximum headshot percentage of every player should not be more than 60% in cs career, if high percentage found they will be kicked immediately from the match.\n" +
            "\nUsing Hacks, Griefing, Teaming, and Rules Violation in KhiladiAdda matches are strictly prohibited. Any such violation will lead to no rewards claim or even your account can be ban.\n" +
            "\nFreefire Maps must be Downloaded by the player before the Match Starts.\n" +
            "\nHand Granade not allowed.\n" +
            "\nHealing Battle not Allowed.\n" +
            "\nIn Duo/Squad Matches, only Captain has to pay the Fees.\n" +
            "\nAlways allow Sound Notification of Khiladi Adda App in your device, so you will remember your match on time. \n" +
            "\nRoom ID and Password will be shared before 5 minutes of the match start time.\n" +
            "\nIf it is late then we will send you a notification about it, but please confirm on WhatsApp Help support once.\n" +
            "\nDon't share match Room ID and Password to anyone, If we found it happened, we will take strict action against you.\n" +
            "\nMatch will start at the exact time if the time will change we will inform you through notification.\n" +
            "\nRoom ID and Password will be shown in the Notification and My leagues live section, once it is updated by KhiladiAdda Team.\n" +
            "\nJoin the Match as soon as possible, because if you don't enter on time, we can't refund your coins.\n" +
            "\nOnce you Joined the Custom Room, don't keep changing your position otherwise, the Host will kick you out from the room.\n" +
            "\nAbusing in Custom Room Chats and anyhow on KhiladiAdda Platform will result in Kick Out on that particular matches.\n" +
            "\nIf you think any cheating is going on, Please Capture some Proof and Complain us about it, KhiladiAdda never tolerate cheating.\n" +
            "\nContact us through WhatsApp on WhatsApp help Support using the \"Contact Us\" option on the home screen for any help or support related to Freefire matches on KhiladiAdda.";

    public static final String RULE_PUBGLOBAL_CLASH = "PUBG\n" +
            "\nMinimum Game Level Should be 30.\n" +
            "\nRoom ID and Password will be shared at the exact start time of the match and match will start within 15 min.\n" +
            "\nRecord Your Gameplay, You Can Be Asked To Provide POV/Recordings.\n" +
            "\nHeadshot(%) Should Not be More than 38.\n" +
            "\nCompensation For Players, Killed By Hackers.\n" +
            "\nHacker’s Account Will be banned permanently.\n" +
            "\nTeam Up Not Allowed.\n" +
            "\nUnfair Activities(Abusive Language) are not allowed. \n \n \n Team Deathmatch \n" +
            "\nMinimum Level Should be 30.\n" +
            "\nRoom ID and Password will be shared at the exact start time of the match and match will start within 5 min.\n" +
            "\nRecord Your Gameplay, You Can Be Asked To Provide POV/Recordings.\n" +
            "\nHeadshot(%)  should not be  38.\n" +
            "\nCompensation For Players, Killed By Hackers.\n" +
            "\nHacker’s account will be banned permanently.\n" +
            "\nUnfair Activities(Abusive Language) are not allowed.";

//    public static final String RULE_ESP_CLASH = "BGMI\n" +
//            "\nMinimum Game Level Should be 50.\n" +
//            "\nRoom ID and Password will be shared at the exact start time of the match and match will start within 15 min.\n" +
//            "\nRecord Your Gameplay, You Can Be Asked To Provide POV/Recordings.\n" +
//            "\nHeadshot(%) Should Not be More than 38.\n" +
//            "\nCompensation For Players, Killed By Hackers.\n" +
//            "\nHacker’s Account Will be banned permanently.\n" +
//            "\nTeam Up Not Allowed.\n" +
//            "\nUnfair Activities(Abusive Language) are not allowed.\n" +
//            "\nPremium And Instant Chat And Calling Support.\n \n \n FreeFire/ FreeFire Max\n" +
//            "\nMinimum Game level should be 50.\n" +
//            "\nPremium And Instant Chat And Calling Support.\n" +
//            "\nRoom ID and Password will be shared at the exact start time of the match and match will start within 15 min.\n" +
//            "\nRecord Your Gameplay, You Can Be Asked To Provide POV/Recordings.\n" +
//            "\nHeadshot(%) Should not be More than 60 in Lifetime Mode.\n" +
//            "\nDouble Vector Not Allowed.\n" +
//            "\nCompensation For Players, Killed By Hackers.\n" +
//            "\nHacker’s Account Will be banned Permanently.\n" +
//            "\nTeam Up Not Allowed.\n" +
//            "\nUnfair Activities(Abusive Language) are not allowed.\n \n \n Team Deathmatch \n" +
//            "\nMinimum Level Should be 50.\n" +
//            "\nRoom ID and Password will be shared at the exact start time of the match and match will start within 10 min.\n" +
//            "\nRecord Your Gameplay, You Can Be Asked To Provide POV/Recordings.\n" +
//            "\nHeadshot(%)  should not be  38.\n" +
//            "\nCompensation For Players, Killed By Hackers.\n" +
//            "\nHacker’s account will be banned permanently.\n" +
//            "\nUnfair Activities(Abusive Language) are not allowed.\n" +
//            "\nPremium And Instant Chat And Calling Support. \n \n \n Clash Squad \n" +
//            "\nMinimum level should be 50.\n" +
//            "\nRoom ID and Password will be shared at the exact start time of the match and match will start within 10 min.\n" +
//            "\nRecord Your Gameplay, You Can Be Asked To Provide POV/Recordings.\n" +
//            "\nHeadshot(%) should not be more than 60 in CS Career.\n" +
//            "\nHealing Battle Not allowed\n" +
//            "\nThrowables Items Not Allowed.\n" +
//            "\nAirDrop Will Be Off.\n" +
//            "\nCompensation For Players, Killed By Hackers.\n" +
//            "\nHacker’s account will be banned permanently.\n" +
//            "\nUnfair Activities(Abusive Language) are not allowed.\n" +
//            "\nPremium And Instant Chat And Calling Support.";

    public static final String RULE_ESP_CLASH = "\nValorant Rules\n\n" +
            "•  Game Level Should Be More Than 20.\n\n" +
            "•  Players Should leave the party after the game starts and can join their own party.\n\n" +
            "•  Overtime Will Be Disabled.\n\n" +
            "•  Changing Riot ID is not allowed at any point after the registration.\n\n" +
            "•  Record Your Gameplay, You Can Be Asked To Provide POV/Recordings.\n\n" +
            "•  Our Admin will add you in the game and make sure to accept his request.\n\n" +
            "•  Match Host Will Invite You at the Time Mentioned On League.\n\n" +
            "•  Match Will Be Started after 10 mins of the Time Mentioned On The League.\n\n" +
            "•  Players are responsible for any game glitch or Internet issue, as matches won’t be paused once started.\n\n" +
            "•  Match Server Will Be \"Mumbai\".\n\n" +
            "•  Hacker's Account Will Be Banned Permanently.\n\n" +
            "•  Teams cannot use non-registered users, ineligible users are considered not registered. Using a player who is not registered is considered a violation and will fall under the smurfing rule. Failure to follow this rule will result in a match overturn.\n\n" +
            "•  Abusing in a custom room(All Chat) and anyhow on KhiladiAdda platform will result in kickout on that particular match.\n\n" +
            "•  Once you join the custom room, Don't change your slot again and again otherwise you will be Kicked out from the Custom.\n\n" +
            "•  Contact us through chat support using the Chat support option on the home screen for any help or support.\n\n\n" +
            "Valorant Solo (1V1) Rules :\n\n\n" +
            "•  Map will be ascent only.\n\n" +
            "•  Try to take the fight on mid only.\n\n" +
            "•  All guns are allowed.\n\n";

    public static final String RULE_JOIN = "Q. How to Join Squad/Duo Match for BattleGround India/Free Fire with Team?\n" + "\n" + "You can Join the BattleGround India/FreeFire Duo and Squad Matches Easily. Here You have to follow the Below Steps accordingly:\n" + "\n" + "Step1: Open the BattleGround India/FreeFire under Games Section.\n" + "\n" + "Step2: Select the League that you want to Join and then Press 'View Button.\n" + "\n" + "Step3: Now Read all the Details and Press 'Create Team.\n" + "\n" + "Step4: Now write your BattleGround India Game Username and Your Team name that you want to Display(It can be any name)\n" + "\n" + "Step5: Press Pay Button. If you don't have enough coin to Join please Add Coin to your Wallet.\n" + "\n" + "Step6: Now Share the Team UniqueId to your Team Member Only, Using UniqueId Your Team member can Enter the Match without Payment. Payment is Only Done by One member of a Team called Leader.\n" + "\n" + "Step 7: Now Your Teammate has to open the same League from BattleGround India/FreeFire Game and Click Join Team.\n" + "\n" + "Step8: Now they have to write their BattleGround India Game Username and Team Unique ID that You have shared.\n" + "\n" + "Step 9: Kudos!!! Your team has Joined the League Successfully.\n" + "\n" + "Remember: Don't Share your Team Unique ID with Anyone otherwise they will enter Your Team for Free and fill their Empty Slot. Khiladi Addda is not Responsible for this.\n" + "\n" + "Q. टीम के साथ BattleGround India / फ्री फायर के लिए स्क्वाड / डुओ मैच में कैसे शामिल हों?\n" + "\n" + "आप BattleGround India / FreeFire Duo और Squad Matches में आसानी से शामिल हो सकते हैं। यहां आपको नीचे दिए गए चरणों का पालन करना होगा:\n" + "\n" + "Step1: गेम्स सेक्शन के तहत BattleGround India / FreeFire खोलें।\n" + "\n" + "Step2: उस लीग का चयन करें जिसे आप ज्वाइन करना चाहते हैं और फिर 'व्यू बटन' दबाएँ।\n" + "\n" + "Step3: अब सभी विवरण पढ़ें और 'टीम बनाएँ' दबाएँ।\n" + "\n" + "Step4: अब अपना BattleGround India Game Username और अपनी टीम का नाम लिखें जिसे आप दिखाना चाहते हैं (यह कोई भी नाम हो सकता है)\n" + "\n" + "स्टेप 5: पे बटन दबाएं। यदि आपके पास पर्याप्त सिक्का नहीं है तो कृपया अपने बटुए में सिक्का जोड़ें।\n" + "\n" + "Step6: अब टीम यूनिक आईडी को अपने टीम मेंबर को ही शेयर करें, यूनिक आईडी का उपयोग करके आपकी टीम का सदस्य बिना भुगतान के मैच में प्रवेश कर सकता है। भुगतान केवल लीडर नामक टीम के एक सदस्य द्वारा किया जाता है।\n" + "\n" + "चरण 7: अब आपके टीममेट को BattleGround India / FreeFire Game से एक ही लीग खोलना है और Join Team पर क्लिक करना है।\n" + "\n" + "Step8: अब उन्हें अपना BattleGround India Game Username और Team Unique ID लिखना है जो आपने शेयर किया है।\n" + "\n" + "चरण 9: यश !!! आपकी टीम लीग में सफलतापूर्वक शामिल हो गई है।\n" + "\n" + "याद रखें: अपनी टीम यूनिक आईडी को किसी के साथ साझा न करें अन्यथा वे आपकी टीम में मुफ्त में प्रवेश करेंगे और अपना खाली स्लॉट भरेंगे। ख़िलाड़ी अद्दा इसके लिए ज़िम्मेदार नहीं है।";

    public static final String RULE_CLASHROYALE = "\nUse of any Hacks, Tricks and Glitch are not " + "allowed." + "\n\nPlayer can use any card in their decks." + "\n\nPlayer can change " + "their decks in between of battles." + "\n\nYour Clash Royale tag id must match in " + "results, otherwise no reward will be given." + "\n\nTournament will be password " + "protected." + "\n\nTournament id and Password will be given to you through " + "notification in App when the Tournament starts and remain for 1 hrs." + "\n\nIf you " + "don't join match on time then you will not be eligible for rewards." + "\n\nIf you " + "are enable to get in the Match due to glitch then contact at help support." + "\n\nFinal Decision and Result will be taken by Admin only." + "\n\nDo not share " + "Tournament Id and Password with anyone.";

    public static final String SUREPASS_TOKEN_NAME = "Authorization";
    public static final String SUREPASS_TOKEN_NAME_VALUE = "Bearer ";
    public static final String SUREPASS_TOKEN_VALUE = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTYyOTcwMzcyNCwianRpIjoiZjM3NDlmZjEtZjY0ZS00MTkyLTg0OGQtZjY2MjEzZTIwYTM3IiwidHlwZSI6ImFjY2VzcyIsImlkZW50aXR5IjoiZGV2LmtoaWxhZGlhZGRhQGFhZGhhYXJhcGkuaW8iLCJuYmYiOjE2Mjk3MDM3MjQsImV4cCI6MTk0NTA2MzcyNCwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.DCYLErPGBaMa7mZQdaE1NgcEJSy6e-qIU9bXp9wQ6zc";

    public static final String FANMATCHCREEN = "HOMEFAN";
    public static final String FANBATTLESCREEN = "BATTLE";
    public static final String FANBATTLECOMBO = "FANBATTLECOMBOS";
    public static final String LUDOHOME = "LUDOHAI";
    public static final String LUDODIALOGSCREEN = "LUDOCHALLENGE";
    public static final String PROFILE = "PROFILE";
    public static final String WALLET = "WALLET";
    public static final String ADDCOINS = "ADDCOINS";
    public static final String HELPSECTION = "HELP";
    public static final String REFERANDEARN = "RE";
    public static final String BGMISOLO = "NEWPUBGS";
    public static final String BGMIDUO = "NEWPUBGD";
    public static final String BGMISQUAD = "NEWPUBGSQUAD";
    public static final String FREEFIRESOLO = "SOLOFREEFIRE";
    public static final String FREEFIREDUO = "DUOFREEFIRE";
    public static final String FREEFIRESQUAD = "FREEFIRESQUAD";
    public static final String FREEFIRECSSOLO = "SOLOFREEFIRECS";
    public static final String FREEFIRECSDUO = "DUOFREEFIRECS";
    public static final String FREEFIRESCSQUAD = "CSFREEFIRESQUAD";
    public static final String TDMSOLO = "TDMSOLO";
    public static final String DIALOG = "DIALOGOPEN";
    public static final String TDMDUO = "TDMDUO";
    public static final String TDMSQUAD = "TDMSQUAD";
    public static final String LEADERBOARD = "OVERALLEADERBOARD";
    public static final String MYLEAGUE = "MYLEAGUE";
    public static final String MYLEAGUETDM = "MYTDMLEAGUE";
    public static final String MYLEAGUEBGMI = "MYLEAGUEBGMI";
    public static final String MYLEAGUEFCS = "MYLEAGUEFCS";
    public static final String QUIZONLY = "QUIZ";
    public static final String GAMEID = "GAMEID";
    public static final String FFMAXSOLO = "FFSOLOMAX";
    public static final String FFMAXDUO = "FFDUOMAX";
    public static final String FFMAXSQUAD = "FFSQUADMAX";
    public static final String MYLEAGUEFFMAX = "MYLEAGUEFFMAX";

    public static final String PESOLO = "PESOLO";
    public static final String PEDUO = "PEDUO";
    public static final String PESQUAD = "PESQUAD";
    public static final String PGSOLO = "PGSOLO";
    public static final String PGDUO = "PGDUO";
    public static final String PGSQUAD = "PGSQUAD";
    public static final String PGNSSOLO = "PGNSSOLO";
    public static final String PGNSDUO = "PGNSDUO";
    public static final String PGNSSQUAD = "PGNSSQUAD";

    public static final String MYLEAGUEPE = "MYLEAGUEPESPORTS";
    public static final String MYLEAGUEPG = "MYLEAGUEPGLOBAL";
    public static final String MYLEAGUEPN = "MYLEAGUEPNEWSTATE";

    public static final int FROM_FANBATTLE_APPFLYER = 10;
    public static final int FROM_APPFLYER_FORMYLEGUESTDM = 1001;
    public static final int FROM_APPFLYER_FORMYLEGUESBGMI = 1002;
    public static final int FROM_APPFLYER_FORMYLEGUESFCS = 1003;
    public static final int FROM_APPFLYER_FORMYLEGUESFFMAX = 1004;
    public static final int FROM_APPFLYER_FORMYLEAGUESPG = 1005;
    public static final int FROM_APPFLYER_FORMYLEGUESPE = 1006;
    public static final int FROM_APPFLYER_FORMYLEGUESFF = 1007;
    public static final int FROM_APPFLYER_FORMYLEGUEPGNS = 1008;

    public static final String GAMEBANNER = "5";
    public static final String APP_ID = "21f543146324b1d2bf25cc721aac4d1ee";
    public static final String LUDO_KOMMUNI_CATEGORY = "Ludo";
    public static final String FANBATTLE_KOMMUNI_CATEGORY = "Fan Battle";
    public static final String FREEFIRE_KOMMUNI_CATEGORY = "Free Fire";
    public static final String QUIZ_KOMMUNI_CATEGORY = "Quiz Query";
    public static final String BGMICATEGORY_KOMMUNICATE = "Battle Ground India";
    public static final String WITHDRAW_AND_DEPOSIT_KOMMUNICATECATEGORY = "Withdraw & Deposit";
    public static final String FREEFIREMAX_KOMMUNICATECATEGORY = "Free Fire Max Query";
    public static final String KYC_KOMMUNICATECATEGORY = "Kyc & Others Query";
    public static final String TEXT_Click_Forgot_Password = "* Click Login with MobileNumber";
    public static final String ESPROTSPERMIUM_KOMMUNICATE = "Premium Esport";

    public static final String PUBGNEWSTATE_KOMMUNICATE = "Pubg NewState";

    public static final String PLAYERTYPE = "PLAYERTYPE";
    public static final String INVEST = "af_invest";
    public static final String GAME = "af_game";
    public static final String LEAGUETYPE = "af_LEAGUETYPE";
    public static final String LEAGUEGAME = "af_LEAGUEGAME";

    public static final String IS_SCRATCH_LUDO = "IS_SCRATCH_LUDO";
    public static final String IS_SCRATCH_FB = "IS_SCRATCH_FB";
    public static final String IS_SCRATCH_LEAGUE = "IS_SCRATCH_LEAGUE";
    public static final String IS_SCRATCH_QUIZ = "IS_SCRATCH_QUIZ";
    public static final String IS_SCRATCH_HTH = "IS_SCRATCH_HTH";
    public static final String IS_SCRATCH_LA = "IS_SCRATCH_LA";
    public static final String IS_SCRATCH_DROID_DO = "IS_SCRATCH_DROID_DO";
    public static final String IS_SCRATCH_WS = "IS_SCRATCH_WS";
    public static final String API_PAYU_HASH = "payumoney/hash/{hash}/{txnid}";
    public static final String API_PAYSHARP = "order/intent";
    public static final String API_PAYSHARP_STATUS = "paysharp/check-payment-processed";
    public static final String API_PAYSHARP_HASH = "paysharp/start-payment/{amount}";
    public static final String API_ZAAKPAY_CHECKSUM = "zaakpay/start-payment";
    public static final String API_APEXPAY_CHECKSUM = "apexpay/start-payment/{amount}";
    public static final String API_APEXPAY_PAYMENT_STATUS = "apexpay/payment-status";
    public static final int FROM_PAYSHARP = 6;
    public static final String API_APEXPAY_PAYOUT = "apexpay/transfer/{bene_id}/{amount}/{otp}";
    public static final String API_PAYSHARP_PAYOUT = "paysharp/transfer/{bene_id}/{amount}/{otp}";
    public static final String API_SEND_OTP_EMAIL = "/user/email-verify/{email}";
    public static final String API_VERIFY_EMAIL = "/user/email-verify-otp/{email}/{otp}";
    public static final String API_UPDATE_EMAIL = "auth/gmail_verification/{email}";
    public static final String TEXT_ENCRYPTION = "secure_pass_key_";
    public static final String WebPayment = "WebPayment";
    public static final int PaymentDone = 1005;
    public static final int PaysharpPaymentDone = 1006;
    public static final int FROM_ANDROID = 1;

    public static final String ENC_KEY = "JFZeaDR6Nm5xQ2c5NFF5NyEoSSR1dFV5JUliISMkSUFRS2xkNEBpI3V0JkJaRmw1aEcqIUZQJWleUUA3eVZARiZmU1MyNyZreE1MNWFLYyQ4OW9aeiNFT2laa2ZTaEc2MVdRWElAVUM5IzFIOGtaRGMhayEyeEJHN0Q5ZXRhOGFWZTlxck41JlhxQmE0bHYhWSVlRXlOdUEySyQ2biUyIXQzREJSZChJTlVjRzZRNUgmS0duMzBtNjYmQ3FobSVkU0RPaHJMYXUkQyZyUjg3UW4qRWh3dFhXQGslMUlrS2laSiZyXiUkIyU5JDZVU3Y0VlBuaGZrUSV5V0R5OXNeVw==";
    public static final String KEY_NEW = "U0VpJWVoYzBRU1o5d2VuJDQkTSo1VjhBMVJaUzc0JF4xOCQpMnZ1a0w3NSU5NzM1OHphNE55RFIoZ21BbTRJNUJMUChtODMheTREWig5bVRsMzhIQW90M09EbGMmMCVZUUUwKCVxNnY0JjZZOTM4KVZTMCpXQ0pPZTReWEpAYVZjNmxlOTVHWDZSdkU2YihWN05jV0xZVUhvb2EwakpNWDh1ekpVbXBtODBrSGpeMjYpY0BqNXNsMERrM3JzQjBnaTg5dChaJSRMUHUhJHFVNHNjNE4hS1lpTGJ5REFGRmNhcUdDaDA0RElBJDYlXmheMTZHbSUjOEIzXm1MKCRFJQ==";
    public static final String VALUE_ENCRYPTION = "ImlkZW50aXR5IjoiZGV2LmtoaWxhZGlhZGRhQGFhZGhhYXJhcGkuaW8iLCJuYmYiOjE2Mjk3MDM3MjQsImV4cCI6MTk0NTA2MzcyNCwidXNlcl9jbGFpbXMiOnsicEi%ehc0QSZ9wen$4$M*5V8A1RZS74$^18$)2vukL75%97358za4NyDR(gmAm4I5BLP(m83!y4DZ(9mTyj7P5G8vIB8ilL!!@@@@**";
    public static final String Enrcy = "(I$utUy%Ib!#$IAQKld4@i#ut&BZFl5hG*!FP%i^Q@7yV@F&fSS27&kxML5aKc$89oZz#EOiZkfShG61WQXI@UC9#1H8kZDc!k!2xBG7D9eta";

    public static final String IS_FAVOURITE = "IS_FAVOURITE";

    public static final String API_DASHBOARD = "user/fetch-dashboard";
    public static final String API_UPDATE_FAVOURITE = "user/update-favourites";

    public static final int FROM_EASEBUZZ = 7;
    public static final String API_EASEBUZZ_HASH = "easebuzz/startPayment";
    public static final String API_EASEBUZZ_SAVE = "easebuzz/savePayment";
    public static final String API_ADD_BENEFICIARY_EASEBUZZ = "easebuzz/createContact";
    public static final String API_EASEBUZZ_PAYOUT = "easebuzz/transferAmount/{beneId}/{amount}/{otp}";

    public static final String API_LEAGUE_NOTIFICATION = "user/update-notification-status";

    public static final String TEXT_RULE_PUBGNS = "PUBG NEW STATE RULES\n\n" +
            "\n* Game Level Should Be More Than 25.\n\n* Win Ration Should Not Be More Than 0.8.\n\n* Match Name(Custom Room Name) And Password Will Be Shared At Exact Time Of The Match And Match Will Start Within 15 Minutes.\n\n* Hackers Account Will Be Banned Permanently.\n\n* Record Your Gameplay You Can Be Asked To Provide Recordings/POV." +
            "\n\n\nHOW TO JOIN AND PLAY\n\n" + "\n* Tap on the Three Dot Icon available in the left corner of the homescreen of PUBG new state.\n" +
            "\n* After that click on Custom Match, then a new window will open containing different custom matches.\n" +
            "\n* Now search the custom room by selecting the server(Aisa), Map And Entering the Required Match Name(Custom Room Name).\n" +
            "\n* Then you will find the custom match. Now Tap on the Join button and enter the password. \n" +
            "\n* Finally you are in a custom room/match.";

    public static final String TEXT_JOIN_PUBGNS = "Q. How to Join Squad/Duo Match for PubG NewState with Team?\n" + "\n" + "You can Join the PubG NewState Duo and Squad Matches Easily. Here You have to follow the Below Steps accordingly:\n" + "\n" + "Step1: Open the PubG NewState under Games Section.\n" + "\n" + "Step2: Select the League that you want to Join and then Press 'View Button.\n" + "\n" + "Step3: Now Read all the Details and Press 'Create Team.\n" + "\n" + "Step4: Now write your PubG NewState Game Username and Your Team name that you want to Display(It can be any name)\n" + "\n" + "Step5: Press Pay Button. If you don't have enough coin to Join please Add Coin to your Wallet.\n" + "\n" + "Step6: Now Share the Team UniqueId to your Team Member Only, Using UniqueId Your Team member can Enter the Match without Payment. Payment is Only Done by One member of a Team called Leader.\n" + "\n" + "Step 7: Now Your Teammate has to open the same League from PubG NewState Game and Click Join Team.\n" + "\n" + "Step8: Now they have to write their PubG NewState Game Username and Team Unique ID that You have shared.\n" + "\n" + "Step 9: Kudos!!! Your team has Joined the League Successfully.\n" + "\n" + "Remember: Don't Share your Team Unique ID with Anyone otherwise they will enter Your Team for Free and fill their Empty Slot. Khiladi Addda is not Responsible for this.\n" + "\n" + "Q. टीम के साथ PubG NewState के लिए स्क्वाड / डुओ मैच में कैसे शामिल हों?\n" + "\n" + "आप PubG NewState Duo और Squad Matches में आसानी से शामिल हो सकते हैं। यहां आपको नीचे दिए गए चरणों का पालन करना होगा:\n" + "\n" + "Step1: गेम्स सेक्शन के तहत PubG NewState खोलें।\n" + "\n" + "Step2: उस लीग का चयन करें जिसे आप ज्वाइन करना चाहते हैं और फिर 'व्यू बटन' दबाएँ।\n" + "\n" + "Step3: अब सभी विवरण पढ़ें और 'टीम बनाएँ' दबाएँ।\n" + "\n" + "Step4: अब अपना PubG NewState Game Username और अपनी टीम का नाम लिखें जिसे आप दिखाना चाहते हैं (यह कोई भी नाम हो सकता है)\n" + "\n" + "स्टेप 5: पे बटन दबाएं। यदि आपके पास पर्याप्त सिक्का नहीं है तो कृपया अपने बटुए में सिक्का जोड़ें।\n" + "\n" + "Step6: अब टीम यूनिक आईडी को अपने टीम मेंबर को ही शेयर करें, यूनिक आईडी का उपयोग करके आपकी टीम का सदस्य बिना भुगतान के मैच में प्रवेश कर सकता है। भुगतान केवल लीडर नामक टीम के एक सदस्य द्वारा किया जाता है।\n" + "\n" + "चरण 7: अब आपके टीममेट को PubG NewState Game से एक ही लीग खोलना है और Join Team पर क्लिक करना है।\n" + "\n" + "Step8: अब उन्हें अपना PubG NewState Game Username और Team Unique ID लिखना है जो आपने शेयर किया है।\n" + "\n" + "चरण 9: यश !!! आपकी टीम लीग में सफलतापूर्वक शामिल हो गई है।\n" + "\n" + "याद रखें: अपनी टीम यूनिक आईडी को किसी के साथ साझा न करें अन्यथा वे आपकी टीम में मुफ्त में प्रवेश करेंगे और अपना खाली स्लॉट भरेंगे। ख़िलाड़ी अद्दा इसके लिए ज़िम्मेदार नहीं है।";

    //MO Enageg
    public static String MatchName = "MatchName";
    public static String ClickedDate = "ClickedDate";
    public static String BattleName = "BattleName";
    public static String ScreenName = "Screen Name";
    public static final String LEAGUE_GAME = "LEAGUE GAME";
    public static final String LEAGUE_TYPE = "LEAGUE TYPE";
    public static String CreateTeam = "Create Team";
    public static final String GAMETYPE = "Game Type";
    public static String EntryFee = "Entry Fee";
    public static String PushFrom = "push_from";
    public static String MoEngage = "moengage";


    //Word Search
    public static final String ALL_QUIZZES = "all_quizzes";
    public static final String WORD_SEARCH_QUIZ_ID = "word_search_quiz_id";
    public static final String WORD_SEARCH_TRENDING_QUIZZES = "trending_quizes";
    public static final String WORD_SEARCH_CATEGORY_QUIZZES = "category_quizzes";
    public static final String WORD_SEARCH_MY_QUIZZES = "category_quizzes";
    public static final String WORD_SEARCH_TYPE = "wordsearch_type";
    public static final String PC_ESPORTS_TYPE = "pcesports_type";
    public static final String COURTPIECE_TYPE = "courtpiece_type";
    public static final String RUMMY_TYPE = "rummy_type";
    public static final String WORD_SEARCH_COLOR_NAME = "color_name";
    public static final String WORD_SEARCH_CATEGORY_NAME = "category_name";

    //Droid-do
    public static final String API_GAME_LIST = "user/gamekite/games";
    public static final String API_HISTORY_GAME_LIST = "user/gamekite/tournament/my-tournaments";
    public static final String API_TRENDINGTOURNAMET_LIST = "user/gamekite/tournaments";
    public static final String GAME_PARTICIPANTS = "user/gamekite/tournament/participants/{tournament_id}";
    public static final String DROIDO_LEADERBOARD = "user/gamekite/tournament/live-leaderboard/{tournament_id}";
    public static final String GAME_START = "/user/gamekite/tournament/start/game";
    public static final String API_MYTOURNAMET_LIST = "user/gamekite/tournament/my-tournaments";
    public static final String API_FILTER_DROIDO = "user/gamekite/tournaments";

    public static final String DROIDO_MY_RANK = "my_ranks";
    public static final String GAME_DROIDO_CLASS_PACKAGE = "com.khiladiadda.droido";
    public static final String DROIDO = "droido";
    public static final String GAMEURLDROIDO = "https://prod.freakx.in/khiladi-adda/v1/index.html";


    public static String WordSearchPackageName = "com.tb.ka.wordsearch";
    public static String LudoAddaPackageName = "com.tb.ludouniverse.in";
    public static String CallBreakPackageName = "com.KhiladiAdda.CourtPiecePro";
    public static final String LUDOADDA_KOMMUNI_CATEGORY = "LudoADDA";
    public static final String LUDO_TOURNAMENT_PACKAGE = "com.khiladiadda.ludoTournament.activity";
    public static final String LUDOTMT_OPP_JOINED = "OPPONENT_JOINED";
    public static final String LUDOTMT_OPP_ROOM_JOINED = "OPPONENT_JOINED_ROOM";
    public static final String WS_VERSION = "WS_VERSION";
    public static final String WS_LINK = "WS_LINK";


    //Neokred PG
    public static final String API_NEOKRED_PG = "neoKred/startPayment";
    public static final String API_NEOKRED_CREATE = "neoKred/createContact";
    public static final String API_NEOKRED_PAYOUT = "neoKred/transferAmount/{beneId}/{amount}/{otp}";

    //Deposit Limit
    public static final String API_ADD_LIMIT = "/profile/modify/add-limit";
    public static final String API_DEPOSIT_GET_LIMIT = "/profile/fetch/add-limit";
    public static final String API_WORD_SEARCH_CATEGORIES = "user/word-search/categories";

    public static final String DROIDO_ID = "id";
    public static final String DROIDO_NAME = "name";
    public static final String DROIDO_IMAGE = "image";
    public static final String DROIDO_ENTRY_FEE = "entryFee";
    public static final String DROIDO_WIN_PRIZE = "winPrize";
    public static final String DROIDO_N_ATTEMPTS = "nAttempts";
    public static final String DROIDO_TOTAL_PARTICIPANTS = "totalparticipants";
    public static final String DROIDO_PLAYED_PARTICIPANTS = "playedparticipants";
    public static final String DROIDO_ENDS_IN = "endsIn";
    public static final String DROIDO_PRIZEPOOL = "prizepool";

    public static int FROM_ALL = 0;
    public static int FROM_CRICKET = 1;
    public static int FROM_FOOTBALL = 2;
    public static int FROM_KABAADI = 3;

    public static final String API_PAYMENT_URL = "phonepay/start-payment/";
    public static final String API_CHECK_PAYMENT_SUCCESS = "phonepay/check-payment-processed/";

    public static final int FROM_PHONEPE_PhonePePAY = 91;
    public static final int FROM_PHONEPE_GPAY = 92;
    public static final int FROM_PHONEPE_PaytmPAY = 93;
    public static final int FROM_PHONEPE = 90;
    public static final String API_GAMER_CASH_VERFIYUSER = "/user/gamercash/link-wallet";
    public static final String API_FETCH_GAMER_CASH = "/user/gamercash/fethc-gc";
    public static final String API_SWITCH_GAMER_CASH = "/user/gamercash/add-coins";
    public static final int FROM_GAMECASE = 9;

    public static final int FROM_GPAY_UPI = 501;
    public static final int FROM_PAYTM_UPI = 502;
    public static final int FROM_PHONEPAY_UPI = 503;

}
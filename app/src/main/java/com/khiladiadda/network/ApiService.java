package com.khiladiadda.network;

import com.khiladiadda.battle.model.BannerResponse;
import com.khiladiadda.battle.model.BattleGroupResponse;
import com.khiladiadda.battle.model.BattleResponse;
import com.khiladiadda.login.TrueCallerRequest;
import com.khiladiadda.login.TrueCallerResponse;
import com.khiladiadda.network.model.request.BajajPayEncryptedRequest;
import com.khiladiadda.network.model.request.PhonepeCheckPaymentRequest;
import com.khiladiadda.network.model.request.PhonepeRequest;
import com.khiladiadda.network.model.request.RaceConditionPayoutRequest;
import com.khiladiadda.network.model.request.UpdateBalanceRequest;
import com.khiladiadda.network.model.request.deposite.DepositLimitRequest;
import com.khiladiadda.network.model.response.BajajPayGetBalanceResponse;
import com.khiladiadda.network.model.response.BajajPayResponse;
import com.khiladiadda.network.model.response.BajajPayVerifyOtpResponse;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;
import com.khiladiadda.network.model.response.CbHistoryRankMainResponse;
import com.khiladiadda.network.model.response.CxBannerMainResponse;
import com.khiladiadda.network.model.response.GamerCashResponse;
import com.khiladiadda.network.model.response.GetGamerCashResponse;
import com.khiladiadda.network.model.response.LeaderboardMainResponse;
import com.khiladiadda.network.model.response.ModeResponse;
import com.khiladiadda.network.model.response.NeokredResponse;
import com.khiladiadda.network.model.response.PhonePePaymentResponse;
import com.khiladiadda.network.model.response.PhonepeCheckPaymentResponse;
import com.khiladiadda.network.model.response.RummyCheckGameResponse;
import com.khiladiadda.network.model.response.RummyHistoryMainResponse;
import com.khiladiadda.network.model.response.RummyRefreshTokenMainResponse;
import com.khiladiadda.network.model.response.RummyResponse;
import com.khiladiadda.network.model.response.TdsResponse;
import com.khiladiadda.network.model.response.UpdateBalanceResponse;
import com.khiladiadda.network.model.response.deposite.DepositLimitMainResponse;
import com.khiladiadda.network.model.response.deposite.FetchDepositLimitMainResponse;
import com.khiladiadda.network.model.response.droid_doresponse.DroidoHistoryGameList;
import com.khiladiadda.network.model.response.droid_doresponse.GameParticipantsDataResponse;
import com.khiladiadda.network.model.response.droid_doresponse.ItemGamesMainResponse;
import com.khiladiadda.network.model.response.droid_doresponse.LeaderBoardDroidoResponse;
import com.khiladiadda.network.model.response.droid_doresponse.MyTournamentResponse;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentDetailRequest;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentDetailResponse;
import com.khiladiadda.network.model.response.droid_doresponse.TrendingTournamentResponse;
import com.khiladiadda.network.model.request.EaseBuzzHashRequest;
import com.khiladiadda.network.model.request.EaseBuzzSaveRequest;
import com.khiladiadda.network.model.request.FcmRequest;
import com.khiladiadda.network.model.request.UpdateFavouriteRequest;
import com.khiladiadda.network.model.request.hth.CreateBattleRequest;
import com.khiladiadda.network.model.request.VoucherRequest;
import com.khiladiadda.network.model.request.hth.UpdateOpponentPlayers;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.BeneficiaryVerifyResponse;
import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.battle.model.JoinGroupRequest;
import com.khiladiadda.battle.model.JoinGroupSubstituteRequest;
import com.khiladiadda.network.model.response.DashboardResponse;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.LudoAddaMainResponse;
import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.AddBeneficiaryRequest;
import com.khiladiadda.network.model.request.AddBeneficieryRazorpay;
import com.khiladiadda.network.model.request.AddCredential;
import com.khiladiadda.network.model.request.CashfreeChecksumRequest;
import com.khiladiadda.network.model.request.CashfreeSavePayment;
import com.khiladiadda.network.model.request.ChangePasswordRequest;
import com.khiladiadda.network.model.request.ChecksumRequest;
import com.khiladiadda.network.model.request.FBBattleParticipantRequest;
import com.khiladiadda.network.model.request.FBRegisterRequest;
import com.khiladiadda.network.model.request.FBVerifyRequest;
import com.khiladiadda.network.model.request.FirebaseRequest;
import com.khiladiadda.network.model.request.ForgetPasswordRequest;
import com.khiladiadda.network.model.request.ForgotPasswordOtpRequest;
import com.khiladiadda.network.model.request.GmailRegisterRequest;
import com.khiladiadda.network.model.request.GmailVerifyRequest;
import com.khiladiadda.network.model.request.LoginRequest;
import com.khiladiadda.network.model.request.LoginVerifyOtpRequest;
import com.khiladiadda.network.model.request.LudoBuddyChallengeRequest;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.LudoErrorRequest;
import com.khiladiadda.network.model.request.LudoResultRequest;
import com.khiladiadda.network.model.request.LudoUpdateRequest;
import com.khiladiadda.network.model.request.ManualWithdrawRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.request.OtpRequest;
import com.khiladiadda.network.model.request.PANVerify;
import com.khiladiadda.network.model.request.PanUpdateRequest;
import com.khiladiadda.network.model.request.PaykunRequest;
import com.khiladiadda.network.model.request.PaymentHistoryRequest;
import com.khiladiadda.network.model.request.PaymentRequest;
import com.khiladiadda.network.model.request.PayuChecksumRequest;
import com.khiladiadda.network.model.request.PayuSavePayment;
import com.khiladiadda.network.model.request.ProfileRequest;
import com.khiladiadda.network.model.request.QuizSubmitRequest;
import com.khiladiadda.network.model.request.RazorpayRequest;
import com.khiladiadda.network.model.request.RazorpayTransferAmountRequest;
import com.khiladiadda.network.model.request.RegistrationRequest;
import com.khiladiadda.network.model.request.ResendOtpRequest;
import com.khiladiadda.network.model.request.ResetPasswordRequest;
import com.khiladiadda.network.model.request.SendOTPRequest;
import com.khiladiadda.network.model.request.StartQuizRequest;
import com.khiladiadda.network.model.request.TransferAmountRequest;
import com.khiladiadda.network.model.request.UpdateChatIdRequest;
import com.khiladiadda.network.model.request.UpdateDOB;
import com.khiladiadda.network.model.request.VerifyMobileRequest;
import com.khiladiadda.network.model.request.VerifyOtpSurepass;
import com.khiladiadda.network.model.response.AddBeneficiaryResponse;
import com.khiladiadda.network.model.response.AllLeaderBoardResponse;
import com.khiladiadda.network.model.response.BeneficiaryResponse;
import com.khiladiadda.network.model.response.BuddyResponse;
import com.khiladiadda.network.model.response.CashfreeChecksumResponse;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.ClashRoyaleFilterReponse;
import com.khiladiadda.network.model.response.CreateTeamResponse;
import com.khiladiadda.network.model.response.FBParticipantResponse;
import com.khiladiadda.network.model.response.FactDetailsResponse;
import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.network.model.response.FaqCategoryResponse;
import com.khiladiadda.network.model.response.HelpResponse;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.LeaderBoardResponse;
import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.network.model.response.LikeFactResponse;
import com.khiladiadda.network.model.response.LoginResponse;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.network.model.response.LudoLeaderboardResponse;
import com.khiladiadda.network.model.response.ManualWithdrawResponse;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.MyLeagueResponse;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.network.model.response.NotifyResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.network.model.response.OverallLeadBoardResponse;
import com.khiladiadda.network.model.response.PanResonse;
import com.khiladiadda.network.model.response.ParticipantResponse;
import com.khiladiadda.network.model.response.PaySharpResponse;
import com.khiladiadda.network.model.response.PaykunOrderResponse;
import com.khiladiadda.network.model.response.PaymentHistoryResponse;
import com.khiladiadda.network.model.response.PaymentStatusRequest;
import com.khiladiadda.network.model.response.PayoutResponse;
import com.khiladiadda.network.model.response.PayuChecksumResponse;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.QuizListResponse;
import com.khiladiadda.network.model.response.QuizParticipantResponse;
import com.khiladiadda.network.model.response.QuizQuestionResponse;
import com.khiladiadda.network.model.response.QuizSubmitResponse;
import com.khiladiadda.network.model.response.RazorpayOrderIdResponse;
import com.khiladiadda.network.model.response.ReferResponse;
import com.khiladiadda.network.model.response.ScratchCardResponse;
import com.khiladiadda.network.model.response.SocialResponse;
import com.khiladiadda.network.model.response.SquadLeaderbordResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;
import com.khiladiadda.network.model.response.TeamResponse;
import com.khiladiadda.network.model.response.TrendinQuizResponse;
import com.khiladiadda.network.model.response.UploadImageResponse;
import com.khiladiadda.network.model.response.UserQuizPlayedResponse;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
import com.khiladiadda.network.model.response.WalletTransactionResponse;
import com.khiladiadda.network.model.response.WithdrawResponse;
import com.khiladiadda.network.model.response.ZaakpayChecksumResponse;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashRequest;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashResponse;
import com.khiladiadda.network.model.response.hth.BattleResponseHTH;
import com.khiladiadda.network.model.response.hth.CreateBattleResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.LeaderBoardHthResponse;
import com.khiladiadda.network.model.response.hth.Result;
import com.khiladiadda.network.model.response.WordSearchCategoriesQuizzesMainResponse;
import com.khiladiadda.network.model.response.WordSearchLeaderBoardMainResponse;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardMainResponse;
import com.khiladiadda.network.model.response.WordSearchMyQuizzesMainResponse;
import com.khiladiadda.network.model.response.WordSearchStartMainResponse;
import com.khiladiadda.network.model.response.WordSearchTrendingMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllPastRoundsMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtJoinMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;
import com.khiladiadda.network.model.response.wordsearch_new.WordSearchCategoryMainResponse;
import com.khiladiadda.playerStats.model.PlayerProfileRes;
import com.khiladiadda.network.model.request.AadhaarRequest;
import com.khiladiadda.network.model.response.AadharCaptchaResponse;
import com.khiladiadda.network.model.response.AadharDetailsResponse;
import com.khiladiadda.network.model.request.AadharUpdateRequest;
import com.khiladiadda.network.model.request.CaptchaRequest;
import com.khiladiadda.network.model.request.CheckAadharRequest;
import com.khiladiadda.network.model.request.GetOtpRequest;
import com.khiladiadda.network.model.response.VerifiBaseResponse;
import com.khiladiadda.network.model.request.VerifyOtpRequest;
import com.khiladiadda.utility.AppConstant;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    //Manually Register
    @POST(AppConstant.API_REGISTER)
    Observable<BaseResponse> register(@Body RegistrationRequest request);

    //Register Verify OTP
    @POST(AppConstant.API_VERIFY_OTP)
    Observable<OtpResponse> verifyOtp(@Body OtpRequest request);

    //Resend OTP
    @POST(AppConstant.API_RESEND_OTP)
    Observable<OtpResponse> resendOTP(@Body ResendOtpRequest request);

    //Gmail Register
    @PUT(AppConstant.API_GMAIL_REGISTER)
    Observable<SocialResponse> gmailRegister(@Body GmailRegisterRequest request);

    //FB Register
    @PUT(AppConstant.API_FB_REGISTER)
    Observable<SocialResponse> fbRegister(@Body FBRegisterRequest request);

    //Credential Login
    @POST(AppConstant.API_LOGIN)
    Observable<BaseResponse> login(@Body LoginRequest request);

    //Login verify otp
    @POST(AppConstant.API_LOGIN_VERIFY_OTP)
    Observable<LoginResponse> verifyOTPonLogin(@Body LoginVerifyOtpRequest request);

    //Gmail Login
    @PUT(AppConstant.API_GMAIL_LOGIN)
    Observable<SocialResponse> gmailLogin(@Body GmailVerifyRequest request);

    //Fb Login
    @PUT(AppConstant.API_FB_LOGIN)
    Observable<SocialResponse> fbLogin(@Body FBVerifyRequest request);

    //Send OTP - Forgot Password
    @POST(AppConstant.API_FORGOT_PASSWORD)
    Observable<BaseResponse> forgetPassword(@Body ForgetPasswordRequest request);

    //Verify OTP - Forgot Password
    @POST(AppConstant.API_FP_VERIFY_OTP)
    Observable<BaseResponse> verifyForgetPasswordOtp(@Body ForgotPasswordOtpRequest request);

    //Reset Password
    @POST(AppConstant.API_RESET_PASSWORD)
    Observable<BaseResponse> resetPassword(@Body ResetPasswordRequest request);

    //Change Password
    @POST(AppConstant.API_CHANGE_PASSWORD)
    Observable<BaseResponse> changePassword(@Body ChangePasswordRequest request);

    //Send OTP on Mobile To verify
    @POST(AppConstant.API_SEND_OTP)
    Observable<OtpResponse> sendOTP(@Body SendOTPRequest request);

    //Verify Mobile Number
    @POST(AppConstant.API_CHANGE_MOBILE)
    Observable<OtpResponse> verifyMobile(@Body VerifyMobileRequest request);

    @GET(AppConstant.API_MASTER)
    Observable<MasterResponse> getMaster();

    //TrendingFacts
    @GET(AppConstant.API_TRENDING_FACTS)
    Observable<FactsResponse> getTrendingFacts();

    //Facts List
    @GET(AppConstant.API_FACTS)
    Observable<FactsResponse> getFacts();

    //Facts Details
    @GET(AppConstant.API_FACT_DETAIL)
    Observable<FactDetailsResponse> getFactDetails(@Path("id") String id);

    //Like Fact
    @POST(AppConstant.API_LIKED_FACTS)
    Observable<LikeFactResponse> likeFact(@Path("id") String id);

    //Bookmark Fact
    @POST(AppConstant.API_BOOKMARK_FACT)
    Observable<LikeFactResponse> bookmarkFact(@Path("id") String id);

    //Bookmarked Facts List
    @GET(AppConstant.API_BOOKMARK_FACT_LIST)
    Observable<FactsResponse> getBookmarkedFacts();

    //Liked Facts List
    @GET(AppConstant.API_LIKED_FACTS_LIST)
    Observable<FactsResponse> getLikedFacts();

    //Profile
    @GET(AppConstant.API_PROFILE)
    Observable<ProfileTransactionResponse> getProfile(@Query("transaction") boolean transaction);

    //Update Profile
    @PUT(AppConstant.API_UPDATE_PROFILE)
    Observable<ProfileResponse> updateProfile(@Body ProfileRequest request);

    //Update DOB
    @PUT(AppConstant.API_UPDATE_PROFILE)
    Observable<ProfileResponse> updateDOB(@Body UpdateDOB request);

    //Update PAN
    @PUT(AppConstant.API_UPDATE_PROFILE)
    Observable<ProfileResponse> updatePAN(@Body PanUpdateRequest request);

    //Check Aadhar
    @POST(AppConstant.API_CHECK_AADHAR)
    Observable<BaseResponse> checkAadhar(@Body CheckAadharRequest request);

    //Update Aadhar
    @PUT(AppConstant.API_UPDATE_PROFILE)
    Observable<ProfileResponse> updateAadhar(@Body AadharUpdateRequest request);

    //Verify PAN
    @POST(AppConstant.VERIFY_PAN)
    Observable<PanResonse> verifyPAN(@Body PANVerify pan);

    //Update Firebase ChatID
    @PUT(AppConstant.API_UPDATE_PROFILE)
    Observable<ProfileResponse> updateChatId(@Body UpdateChatIdRequest request);

    //Upload
    @Multipart
    @POST(AppConstant.API_UPLOAD_IMAGE)
    Observable<UploadImageResponse> uploadImage(@Part MultipartBody.Part file, @Query("type") int type);

    //Trending Quiz List
    @GET(AppConstant.API_TRENDING_QUIZ)
    Observable<TrendinQuizResponse> getTrendingQuiz();

    //Dashboard
    @GET(AppConstant.API_DASHBOARD)
    Observable<DashboardResponse> getDashboardData();

    //Update Favourite
    @PUT(AppConstant.API_UPDATE_FAVOURITE)
    Observable<BaseResponse> updateFavourite(@Body UpdateFavouriteRequest request);

    //Quiz Api
    @GET(AppConstant.API_QUIZ_LIST)
    Observable<QuizListResponse> getQuizList(@Path("id") String id, @Query("upcoming") boolean upcoming, @Query("past") boolean past, @Query("live") boolean live);

    //Quiz isPlayed - inside live quiz
    @GET(AppConstant.API_QUIZ_DETAIL)
    Observable<UserQuizPlayedResponse> getUserPlayedQuiz(@Path("id") String id, @Query("profile") boolean profile);

    //Quiz Question List
    @GET(AppConstant.API_QUIZ_QUESTION)
    Observable<QuizQuestionResponse> getQuizQuestion(@Path("id") String id);

    //Start Quiz
    @POST(AppConstant.API_START_QUIZ)
    Observable<StartQuizResponse> startQuiz(@Body StartQuizRequest request);

    //Submit Quiz
    @POST(AppConstant.API_SUBMIT_QUIZ)
    Observable<QuizSubmitResponse> submitQuiz(@Body QuizSubmitRequest request);

    //Get Quiz Participant
    @GET(AppConstant.API_QUIZ_PARTICIPANT)
    Observable<QuizParticipantResponse> getQuizParticipant(@Path("id") String id, @Query("page") int page, @Query("limit") int limit);

    //LeaderBoardAll - Quiz
    @GET(AppConstant.API_QUIZ_LEADERBOARD_ALL)
    Observable<AllLeaderBoardResponse> getLeaderBoardAll(@Query("page") int page, @Query("limit") int limit);

    //LeaderBoardMonth - Quiz
    @GET(AppConstant.API_QUIZ_LEADERBOARD_MONTHLY)
    Observable<AllLeaderBoardResponse> getLeaderboardMonth(@Query("page") int page, @Query("limit") int limit, @Query("type") int type);

    //Leaderboard - Quiz Result/End
    @GET(AppConstant.API_QUIZ_LEADERBOARD)
    Observable<LeaderBoardResponse> getLeaderBoard(@Path("id") String id);

    //LeaderBoardAll - Game
    @GET(AppConstant.API_GAME_LEADERBOARD_ALL)
    Observable<AllLeaderBoardResponse> getGameLeaderBoardAll(@Path("id") String id, @Query("page") int page, @Query("limit") int limit, @Query("type") int type);

    //LeaderBoardMonth - Game
    @GET(AppConstant.API_GAME_LEADERBOARD_MONTHLY)
    Observable<AllLeaderBoardResponse> getGameLeaderBoardMonth(@Path("id") String id, @Query("page") int page, @Query("limit") int limit);

    //LeaderBoardByLeagueId - Game
    @GET(AppConstant.API_GAME_LEADERBOARD)
    Observable<AllLeaderBoardResponse> getGameLeaderBoardById(@Path("id") String id, @Query("page") int page, @Query("limit") int limit);

    //LeaderBoardByTeamId - Squad_Duo
    @GET(AppConstant.API_GAME_TEAM_LEADERBOARD)
    Observable<SquadLeaderbordResponse> getSquadDuoLeaderBoard(@Path("id") String id, @Query("page") int page, @Query("limit") int limit);

    //LeaderBoardMonth - Ludo
    @GET(AppConstant.API_CONTEST_LEADERBOARD)
    Observable<LudoLeaderboardResponse> getLudoLeaderBoard(@Query("page") int page, @Query("limit") int limit, @Query("type") String type, @Query("contest_type") int contestType);

    //get Game Category
    @GET(AppConstant.API_GAME_CATEGORY)
    Observable<ClashRoyaleFilterReponse> getGameCategory(@Path("id") String id);

    //Get CR League List on Filter basis
    @GET(AppConstant.API_CR_LEAGUE)
    Observable<LeagueListReponse> getCRLeague(@Path("id") String id, @Query("type") int type);

    //Get League Participant
    @GET(AppConstant.API_LEAGUE_PARTICIPANT)
    Observable<ParticipantResponse> getParticipant(@Path("id") String id);

    //Get FB Group Participant
    @POST(AppConstant.API_FB_GROUP_PARTICIPANT)
    Observable<FBParticipantResponse> getFBGroupParticipant(@Body FBBattleParticipantRequest request, @Query("page") int page, @Query("limit") int limit, @Query("is_leaderboard") boolean isLeaderboard);

    //Get FB Battle Participant
    @POST(AppConstant.API_FB_GROUP_PARTICIPANT)
    Observable<FBParticipantResponse> getFBBattleParticipant(@Body FBBattleParticipantRequest request, @Query("page") int page, @Query("limit") int limit);

    //Get FB Match Participant
    @POST(AppConstant.API_FB_GROUP_PARTICIPANT)
    Observable<FBParticipantResponse> getFBMatchParticipant(@Body FBBattleParticipantRequest request, @Query("page") int page, @Query("limit") int limit);

    //Attempt League - Add UserId & Password
    @POST(AppConstant.API_JOIN_LEAGUE)
    Observable<StartQuizResponse> addCredential(@Body AddCredential request, @Path("id") String id);

    //Create Team
    @POST(AppConstant.API_CREATE_TEAM)
    Observable<CreateTeamResponse> createTeam(@Body AddCredential request, @Path("id") String id);

    //My League - PubG, CallOfDuty
    @GET(AppConstant.API_MY_LEAGUE)
    Observable<MyLeagueResponse> getMyLeague(@Path("id") String id, @Query("upcoming") boolean upcoming, @Query("past") boolean past, @Query("live") boolean live);

    //Get League My Team Details
    @GET(AppConstant.API_MY_TEAM)
    Observable<MyTeamResponse> getMyTeam(@Path("id") String id);

    //Get League All Teams
    @GET(AppConstant.API_TEAM_ALL)
    Observable<TeamResponse> getTeam(@Path("id") String id);

    //Get all referred users list
    @GET(AppConstant.API_REFER_HISTORY)
    Observable<ReferResponse> getRefer();

    //Update FCM ID
    @PUT(AppConstant.API_FIREBASE)
    Observable<BaseResponse> updateFirebaseId(@Body FirebaseRequest request);

    //Get help list
    @GET(AppConstant.API_CATEGORY_FAQ)
    Observable<FaqCategoryResponse> getHelpCategory();

    @GET(AppConstant.API_FAQ)
    Observable<HelpResponse> getHelp(@Query("category_id") String id, @Query("name") String name);

    //Ludo
    @GET(AppConstant.API_CONTEST)
    Observable<LudoContestResponse> getLudoContestList(@Query("date") String date, @Query("contest_type") String contest_type, @Query("banners") boolean banners, @Query("banner_type") String banner_type, @Query("profile") boolean profile, @Query("mode") int mode, @Query("entry_fees_filter") int entry_fees_filter, @Query("from") int from, @Query("to") int to);

    @GET(AppConstant.API_CONTEST)
    Observable<LudoContestResponse> getAllLudoContestList(@Query("contest_type") String contest_type, @Query("page") int page, @Query("limit") int limit);

    @POST(AppConstant.API_CONTEST)
    Observable<BaseResponse> addLudoChallengeResponse(@Body LudoContestRequest request);

    @PUT(AppConstant.API_JOIN_CONTEST)
    Observable<BaseResponse> joinLudoContest(@Path("contest_id") String contestId, @Body OpponentLudoRequest opponentRequest);

    @PUT(AppConstant.API_CANCEL_CONTEST)
    Observable<BaseResponse> cancelLudoContest(@Path("contest_id") String contestId);

    @PUT(AppConstant.API_ROOMCODE)
    Observable<BaseResponse> updateLudoContest(@Path("contest_id") String contestId, @Body LudoUpdateRequest ludoUpdateRequest);

    @PUT(AppConstant.API_UPDATE_RESULT)
    Observable<BaseResponse> ludoResultResponse(@Path("contest_id") String contestId, @Body LudoResultRequest ludoResultRequest);

    @PUT(AppConstant.API_CONTEST_REASON)
    Observable<BaseResponse> ludoError(@Path("contest_id") String contestId, @Body LudoErrorRequest request);

    @GET(AppConstant.API_BUDDY_OPPONENT)
    Observable<BuddyResponse> getBuddyList(@Query("contest_type") String contest_type);

    @POST(AppConstant.API_NOTIFY_OPPONENT)
    Observable<BaseResponse> sendChallengeRequest(@Body LudoBuddyChallengeRequest request);

    @GET(AppConstant.API_USERS_TOKEN)
    Observable<NotifyResponse> getOpponentTokens(@Path("contestId") String id);

    //Paytm
    @POST(AppConstant.API_PAYTM_CHECKSUM)
    Observable<ChecksumResponse> getPaytmChecksumHash(@Body ChecksumRequest request);

    @POST(AppConstant.API_PAYTM_PAYMENT)
    Observable<BaseResponse> savePaytmPayment(@Body PaymentRequest request);

    //Cashfree
    @POST(AppConstant.API_CASHFREE_CHECKHSUM)
    Observable<CashfreeChecksumResponse> getCashfreeChecksumHash(@Body CashfreeChecksumRequest request);

    @POST(AppConstant.API_CASHFREE_PAYMENT)
    Observable<BaseResponse> saveCashfreePayment(@Body CashfreeSavePayment request);

    //Payu
    @POST(AppConstant.API_PAYU_CHECKSUM)
    Observable<PayuChecksumResponse> getPayuChecksum(@Body PayuChecksumRequest request);

    @POST(AppConstant.API_PAYU_PAYMENT)
    Observable<BaseResponse> savePayuPayment(@Body PayuSavePayment request);

    //Razorpay
    @POST(AppConstant.API_RAZORPAY_ORDERID)
    Observable<RazorpayOrderIdResponse> getRazorpay(@Body CashfreeChecksumRequest request);

    @POST(AppConstant.API_RAZORPAY_PAYMENT)
    Observable<BaseResponse> saveRazorpay(@Body RazorpayRequest request);

    //Paykun
    @POST(AppConstant.API_PAYKUN_ORDERID)
    Observable<PaykunOrderResponse> getPaykunOrderId(@Body CashfreeChecksumRequest request);

    @POST(AppConstant.API_PAYKUN_PAYMENT)
    Observable<BaseResponse> savePaykunPayment(@Body PaykunRequest request, @Path("paymentId") String paymentId, @Path("orderId") String orderId);

    //Get All Transaction
    @GET(AppConstant.API_TRANSACTION)
    Observable<WalletTransactionResponse> getAllTransaction(@Query("page") int page, @Query("limit") int limit);

    //Get All Payment Details
    @POST(AppConstant.API_PAYMENT_DETAILS)
    Observable<PaymentHistoryResponse> getPaymentDetails(@Body PaymentHistoryRequest request);

    //Transfer Coins
//    @POST(AppConstant.API_TRANSFER_COINS)
//    Observable<BaseResponse> transferCoins(@Body TransferRequest request);
    //Get userData for Mobile - Transfer Coin
//    @GET(AppConstant.API_MATE_DATA)
//    Observable<MateResponse> getUserData(@Path("id") String id);

    @POST(AppConstant.API_WITHDRAW_REQUEST)
    Observable<BaseResponse> doManualWithdraw(@Body ManualWithdrawRequest request);

    @GET(AppConstant.API_WITHDRAW_HISTORY)
    Observable<WithdrawResponse> getWithdraw(@Query("page") int page, @Query("limit") int limit);

    //Manual Withdraw List
    @GET(AppConstant.API_MANUAL_WITHDRAW_HISTORY)
    Observable<ManualWithdrawResponse> getManualWithdraw();

    //Cashfree AddBeneficiary
    @POST(AppConstant.API_ADD_BENEFICIARY)
    Observable<AddBeneficiaryResponse> addBeneficiary(@Body AddBeneficiaryRequest request);

    @POST(AppConstant.API_TRANSFER_AMOUNT_BANK)
    Observable<PayoutResponse> transferAmount(@Body TransferAmountRequest request, @Path("id") String id);

    //Razorpay
    @POST(AppConstant.API_RAZORPAY_ADD_BENEFICIARY)
    Observable<AddBeneficiaryResponse> addRazorpayBeneficiary(@Body AddBeneficieryRazorpay request);

    @POST(AppConstant.API_RAZORPAY_TRANSFER_AMOUNT)
    Observable<PayoutResponse> transferRazorpayAmount(@Body RazorpayTransferAmountRequest request);

    @GET(AppConstant.API_GET_BENEFICIARY)
    Observable<BeneficiaryResponse> getBeneficiaryList();

    @GET(AppConstant.API_WITHDRAW_LIMIT)
    Observable<WIthdrawLimitResponse> getWithdrawLimit();

    @DELETE(AppConstant.API_REMOVE_BENEFICIARY)
    Observable<BaseResponse> deleteBeneficiary(@Path("beneId") String id);

    @GET(AppConstant.API_VERSION)
    Observable<VersionResponse> getVersionDetails();

    //FanBattle
    @GET(AppConstant.API_FAN_BATTLE)
    Observable<MatchResponse> getMatchList(@Query("game_id") String game_id, @Query("banners") boolean banner);

    @GET(AppConstant.API_BATTLE)
    Observable<BattleResponse> getBattleList(@Query("match_id") String matchId, @Query("is_played") boolean isPlayed, @Query("live") boolean live, @Query("past") boolean past);

    @GET(AppConstant.API_GROUP)
    Observable<BattleGroupResponse> getGroupList(@Query("battle_id") String battleId, @Query("is_reverse") boolean is_reverse, @Query("profile") boolean profile);

    @POST(AppConstant.API_JOIN_GROUP)
    Observable<BaseResponse> joinBattleGroup(@Body JoinGroupRequest request, @Path("id") String id);

    @POST(AppConstant.API_JOIN_SUBSTITUTE_GROUP)
    Observable<BaseResponse> joinSubstituteGroup(@Body JoinGroupSubstituteRequest request);

    @GET(AppConstant.API_MY_FAN_BATTLE)
    Observable<MatchResponse> getMyFanLeague(@Query("upcoming") boolean upcoming, @Query("past") boolean past, @Query("live") boolean live);

    @GET(AppConstant.API_BANNER)
    Observable<BannerResponse> getBanner(@Query("type") int type);

    @PUT(AppConstant.API_CANCEL_GROUP)
    Observable<BaseResponse> cancelGroup(@Path("id") String id);

    //Get All Payment Details
    @PUT(AppConstant.API_PAYMENT_STATUS)
    Observable<BaseResponse> getPaymentStatus(@Path("paymentId") String id);

    //Get OverALL LEADBOARD
    @GET(AppConstant.API_OVERALLLEADBOARD)
    Observable<OverallLeadBoardResponse> getOverAllLeaderboard(@Query("type") int type, @Query("page") int page, @Query("limit") int limit);

    @GET(AppConstant.CRIC_API_SCORCE)
    Observable<CricScorce> getlivescore(@Query("apikey") String apikey, @Query("unique_id") int matchid);

    @GET(AppConstant.PLAYER_STATS)
    Observable<PlayerProfileRes> getPlayerProfile(@Query("apikey") String apikey, @Query("pid") int pid);

    //Verifi
    @POST(AppConstant.API_AADHAR_GET_CAPTCHA)
    Observable<AadharCaptchaResponse> getCaptcha(@Body CaptchaRequest request);

    @POST(AppConstant.API_AADHAR_GET_OTP)
    Observable<VerifiBaseResponse> getAadhaarOTP(@Body GetOtpRequest request);

    @POST(AppConstant.API_AADHAR_VERIFY_OTP)
    Observable<VerifiBaseResponse> verifyAadhaarOTP(@Body VerifyOtpRequest request);

    @POST(AppConstant.API_AADHAR_DETAILS)
    Observable<AadharDetailsResponse> getKYCDetails(@Body AadhaarRequest request);

    //Update Aadhaar
    @PUT(AppConstant.API_UPDATE_PROFILE)
    Observable<ProfileResponse> updateAadhaar(@Body AadharUpdateRequest request);

    //Check aadhar surepass
    @POST(AppConstant.API_AADHAR_VERIFY_OTP_SUREPASS_SERVER)
    Observable<ProfileResponse> verifyAadharOTPSurepass(@Body VerifyOtpSurepass request);

    //Invoice
    @GET(AppConstant.API_INVOICE)
    Observable<InvoiceResponse> getInvoice(@Path("id") String id);

    //Time
    @GET(AppConstant.API_QUIZ_TIME)
    Observable<BaseResponse> getQuizTime(@Path("id") String id);

    //Create Battle
    @POST(AppConstant.API_CREATE_GROUP)
    Observable<CreateBattleResponse> createBattleGroup(@Body CreateBattleRequest request);

    //Update Battle
    @PUT(AppConstant.API_UPDATE_BATTLE)
    Observable<BaseResponse> updateBattleGroup(@Body CreateBattleRequest request, @Path("groupId") String groupId);

    //My Battles
    @GET(AppConstant.API_MY_BATTLE)
    Observable<BattleResponseHTH> getMyBattles(@Query("match_id") String matchId, @Query("battle_id") String battleId, @Query("user_id") boolean user, @Query("highestBattle") boolean highestBattle, @Query("lowestBattle") boolean lowestBattle, @Query("newestBattle") boolean newestBattle);

    //Apply Coupon
    @GET(AppConstant.API_APPLY_COUPON)
    Observable<InvoiceResponse> applyCoupon(@Path("coupon") String couponCode);

    //Get ScatchCards
    @GET(AppConstant.API_SCRATCH_CARDS)
    Observable<ScratchCardResponse> getAllScratchCards(@Query("cardType") int type);

    //Scratched Card
    @POST(AppConstant.API_SCRATCHED_CARD)
    Observable<BaseResponse> scratchedCard(@Path("scratched") String scratchcard);

    //APPLY Voucher
    @POST(AppConstant.API_APPLY_VOUCHER)
    Observable<BaseResponse> applyVocher(@Body VoucherRequest request);

    // GET HTH BATTLE
    @GET(AppConstant.API_HTH_MATHCES)
    Observable<HTHMainResponse> getMyHTHBattle(@Query("banners") boolean banner, @Query("match_id") String id, @Query("type") int type);

    //GET HTH MATCHES LEGUES
    @GET(AppConstant.API_HTH_LEGUES)
    Observable<HTHMainResponse> getMylegues(@Query("upcoming") boolean upcoming, @Query("past") boolean past, @Query("live") boolean live);

    // CANCEL HTH BATTLE
    @DELETE(AppConstant.API_HTH_CANCELBATTLE)
    Observable<HTHCancelResponse> CancelBattle(@Path("id") String groupId);

    //GET HTH RESULT
    @GET(AppConstant.API_RESULT_BATTLE)
    Observable<Result> getResult(@Path("battleId") String groupId);

    //GET HTH OVERALL LEADERBOARD
    @GET(AppConstant.API_HTH_OVERALLLEADBOARD)
    Observable<LeaderBoardHthResponse> getOverHTHAllLeaderboard(@Query("page") int page, @Query("limit") int limit, @Query("type") String type);

    //GET HTH UPDATE OPPONENT PLAYERS
    @PUT(AppConstant.API_UPDATE_OPPONENTPLAYERS)
    Observable<BaseResponse> updateOpponentPlayer(@Path("battleId") String id, @Body UpdateOpponentPlayers request);

    //GET PLAYER STATUS
    @POST(AppConstant.API_KAPLAYERS)
    Observable<HTHMainResponse> getPlayerSatus(@Path("matchId") String id, @Body UpdateOpponentPlayers request);

    //GET MATCH STATUS
    @GET(AppConstant.API_MATCHSTATUS)
    Observable<HTHMainResponse> getMatchStatus(@Path("matchId") String id);

    @GET(AppConstant.API_VERIFY_BENEFICIARY)
    Observable<BeneficiaryVerifyResponse> verifyBeneficiary(@Path("id") String beneficiaryId);

    //Ludo Universe
    @GET(AppConstant.API_LU_CONTEST)
    Observable<LudoContestResponse> getList(@Query("date") String date, @Query("contest_type") String contest_type, @Query("banners") boolean banners, @Query("banner_type") String banner_type, @Query("profile") boolean profile, @Query("mode") int mode, @Query("entry_fees_filter") int entry_fees_filter, @Query("from") int from, @Query("to") int to, @Query("contest_mode") int contest_mode);

    @GET(AppConstant.API_LU_MY_CONTEST)
    Observable<LudoContestResponse> getAllList(@Query("page") int page, @Query("limit") int limit,
                                               @Query("contest_mode") int contestMode);

    @POST(AppConstant.API_LU_ADD_CONTEST)
    Observable<BaseResponse> addChallenge(@Body LudoContestRequest request);

    @POST(AppConstant.API_LU_JOIN_CONTEST)
    Observable<BaseResponse> acceptChallenge(@Path("contest_id") String contestId);

    @DELETE(AppConstant.API_LU_CANCEL_CONTEST)
    Observable<BaseResponse> cancelChallenge(@Path("contest_id") String contestId);

    @GET(AppConstant.API_LU_BUDDY_OPPONENT)
    Observable<BuddyResponse> getLUBuddyList(@Query("contest_mode") int contestMode);

    @GET(AppConstant.API_LU_STATUS)
    Observable<BaseResponse> getLUStatus(@Path("contest_id") String contestId);

    //Ludo Universe Mode
    @GET(AppConstant.API_LU_MODE)
    Observable<ModeResponse> getMode(@Path("bannerType") String banner_type);
    //Zaakpay
    @POST(AppConstant.API_ZAAKPAY_CHECKSUM)
    Observable<ZaakpayChecksumResponse> getZaakpayChecksumHash();

    //ApexPay
    @GET(AppConstant.API_APEXPAY_CHECKSUM)
    Observable<ApexPayChecksumResponse> getApexPayChecksumHash(@Path("amount") String amount, @Query("coupon") String couponCode);

    //ApexPay
    @POST(AppConstant.API_APEXPAY_PAYMENT_STATUS)
    Observable<ApexPayChecksumResponse> getApexPayStatus(@Body PaymentStatusRequest request);

    //PaySharp
//    @POST(AppConstant.API_PAYSHARP)
//    Observable<PaySharpResponse> getPaySharp(@Body PaySharpRequest request);

    //PaySharp
    @GET(AppConstant.API_PAYSHARP_HASH)
    Observable<PaySharpResponse> getPaySharp(@Path("amount") String amount, @Query("coupon") String couponCode);

    //PaySharp
    @POST(AppConstant.API_PAYSHARP_STATUS)
    Observable<BaseResponse> getPaySharpStatus(@Body PaymentStatusRequest request);

    //Payu Hash
    @GET(AppConstant.API_PAYU_HASH)
    Observable<PayuChecksumResponse> getPayuHash(@Path("hash") String hash, @Path("txnid") String orderId);

    //Apex payout
    @GET(AppConstant.API_APEXPAY_PAYOUT)
    Observable<PayoutResponse> onApexPayTransfer(@Path("bene_id") String beneficiaryId, @Path("amount") String amount, @Path("otp") String otp);

    //Paysharp Payout
    @GET(AppConstant.API_PAYSHARP_PAYOUT)
    Observable<PayoutResponse> onPaySharpTransfer(@Path("bene_id") String beneficiaryId, @Path("amount") String amount, @Path("otp") String otp);

    //Send OTP
    @GET(AppConstant.API_SEND_OTP_EMAIL)
    Observable<BaseResponse> sendOtpEmail(@Path("email") String email);

    //Verify Email
    @GET(AppConstant.API_VERIFY_EMAIL)
    Observable<BaseResponse> verifyEmail(@Path("email") String email, @Path("otp") String otp);

    //Update Email
    @GET(AppConstant.API_UPDATE_EMAIL)
    Observable<BaseResponse> updateEmail(@Path("email") String email);

    //LudoAdda
    @GET(AppConstant.API_LU_OVERALLEADERBOARD)
    Observable<LudoAddaMainResponse> getOverLudoAddaAllLeaderboard(@Query("page") int page, @Query("limit") int limit, @Query("type") String type);

    //Cashfree AddBeneficiary
    @POST(AppConstant.API_ADD_CASHFREE_BENEFICIARY)
    Observable<AddBeneficiaryResponse> addCashfreeBeneficiary(@Body AddBeneficiaryRequest request);

    //EaseBuzz
    @POST(AppConstant.API_EASEBUZZ_HASH)
    Observable<ChecksumResponse> getEaseBuzzHash(@Body EaseBuzzHashRequest request);

    @POST(AppConstant.API_EASEBUZZ_SAVE)
    Observable<BaseResponse> saveEaseBuzzPayment(@Body EaseBuzzSaveRequest request);

    //Easebuzz AddBeneficiary
    @POST(AppConstant.API_ADD_BENEFICIARY_EASEBUZZ)
    Observable<AddBeneficiaryResponse> addBeneficiaryEasebuzz(@Body AddBeneficiaryRequest request);

    //Apex payout
    @POST(AppConstant.API_EASEBUZZ_PAYOUT)
    Observable<PayoutResponse> onEasebuzzTransfer(@Path("beneId") String beneficiaryId, @Path("amount") double amount, @Path("otp") String otp);

    @PUT(AppConstant.API_LEAGUE_NOTIFICATION)
    Observable<BaseResponse> updateLeague(@Body FcmRequest request);

    //Word Search
    @GET(AppConstant.API_WORD_SEARCH_TRENDING_QUIZ)
    Observable<WordSearchTrendingMainResponse> getAllQuizzes();

    @GET(AppConstant.API_WORD_SEARCH_CATEGORIES_QUIZZES)
    Observable<WordSearchCategoriesQuizzesMainResponse> getCategoryQuizzes(@Path("quizId") String quizId);

    @GET(AppConstant.API_WORD_SEARCH_QUIZ_PARTICIPANTS)
    Observable<WordSearchLeaderBoardMainResponse> getQuizParticipants(@Path("quizId") String quizId);

    @POST(AppConstant.API_WORD_SEARCH_START_QUIZ)
    Observable<WordSearchStartMainResponse> getStartQuiz(@Path("quizId") String quizId);

    @GET(AppConstant.API_WORD_SEARCH_MY_QUZZIES)
    Observable<WordSearchMyQuizzesMainResponse> getMyQuizzesQuiz();

    @GET(AppConstant.API_WORD_SEARCH_LIVE_LEADERBOARD)
    Observable<WordSearchLiveLeaderBoardMainResponse> getLiveLeaderBoard(@Path("quizId") String quizId);

    //GET Game List Drido
    @POST(AppConstant.API_GAME_LIST)
    Observable<ItemGamesMainResponse> getGameList();

    //GET History Game List Drido
    @GET(AppConstant.API_HISTORY_GAME_LIST)
    Observable<DroidoHistoryGameList> getDroidoHistoryGameList();
  //  Observable<DroidoHistoryGameList> getDroidoHistoryGameList(@Query("page") int page, @Query("limit")int limit);

    //Get Trending and more TournamentGames
    @GET(AppConstant.API_TRENDINGTOURNAMET_LIST)
    Observable<TrendingTournamentResponse> getTrendingTournamentList();

    //Get MyTournament
    @GET(AppConstant.API_MYTOURNAMET_LIST)
//    @GET("user/gamekite/tournaments")
    Observable<MyTournamentResponse> getMyTournamentList();


    //Get MyTournament
    @GET(AppConstant.API_FILTER_DROIDO)
    Observable<TrendingTournamentResponse> getFilterTournament(@Query("type") int type);

    @POST(AppConstant.GAME_START)
    Observable<TournamentDetailResponse> getTournamentStart(@Body TournamentDetailRequest request);

    //Get Participants game
    @GET(AppConstant.GAME_PARTICIPANTS)
    Observable<GameParticipantsDataResponse> getParticipantsList(@Path("tournament_id") String id);

    //Get LaederBoardDroido game
    @GET(AppConstant.DROIDO_LEADERBOARD)
    Observable<LeaderBoardDroidoResponse> getLeaderBoardDroidoList(@Path("tournament_id") String id);

    //LeaderBoardMonth - WS
    @GET(AppConstant.API_WS_OVERALLEADERBOARD)
    Observable<AllLeaderBoardResponse> getLeaderboardWSMonth(@Query("page") int page, @Query("limit") int limit, @Query("type") String type);

    //LeaderBoardAll - Droid DO
    @GET(AppConstant.API_DROID_DO_OVERALLEADERBOARD)
    Observable<AllLeaderBoardResponse> getLeaderBoardGKAll(@Query("page") int page, @Query("limit") int limit, @Query("type") String type);


    //LeaderBoardAll - LudoTournament
    @GET(AppConstant.API_LUDO_TOURNAMENT)
    Observable<LeaderboardMainResponse> getLeaderBoardLudoTournament(@Query("type") String type, @Query("page") int page, @Query("limit") int limit);

    //LeaderBoardAll - CallBreak
    @GET(AppConstant.API_CALL_BREAK)
    Observable<LeaderboardMainResponse> getLeaderBoardCallBreak(@Query("type") String type, @Query("page") int page, @Query("limit") int limit);

    //LeaderBoardAll - Rummy
    @GET(AppConstant.API_RUMMY)
    Observable<LeaderboardMainResponse> getLeaderBoardRummy(@Query("type") String type, @Query("page") int page, @Query("limit") int limit);




    //Neokred
    @POST(AppConstant.API_NEOKRED_PG)
    Observable<NeokredResponse> checkNeokredPG(@Body EaseBuzzSaveRequest request);

    @POST(AppConstant.API_NEOKRED_CREATE)
    Observable<AddBeneficiaryResponse> addBeneficiaryNeokred(@Body AddBeneficiaryRequest request);

    @POST(AppConstant.API_NEOKRED_PAYOUT)
    Observable<PayoutResponse> onNeokredTransfer(@Path("beneId") String beneficiaryId, @Path("amount") double amount, @Path("otp") String otp);

    //For TrueCaller Api
    @POST(AppConstant.API_TRUECALLER)
    Observable<TrueCallerResponse> trueCallerVerify(@Body TrueCallerRequest trueCallerRequest);

    //Deposit Limit
    @POST(AppConstant.API_ADD_LIMIT)
    Observable<DepositLimitMainResponse> addDepositLimit(@Body DepositLimitRequest request);

    @GET(AppConstant.API_DEPOSIT_GET_LIMIT)
    Observable<FetchDepositLimitMainResponse> fetchDepositLimit();

    //New Added
    @GET(AppConstant.API_WORD_SEARCH_CATEGORIES)
    Observable<WordSearchCategoryMainResponse> getWordSearchCategories();

    //RACE-CONDITION payout
    @POST(AppConstant.API_RACE_CONDITION)
    Observable<PayoutResponse> onRaceFreeTransfer(@Body RaceConditionPayoutRequest raceConditionPayoutRequest);

    //RACE-CONDITION payout Final
    @GET(AppConstant.API_RACE_CONDITION_FINAL)
    Observable<PayoutResponse> onRaceFreeTransferFinal(@Path("beneId") String beneficiaryId, @Path("amount") String amount, @Path("otp") String otp);

    //Instant Payout
    @GET(AppConstant.API_INSTANT_PAY)
    Observable<PayoutResponse> onInstantPayTransfer(@Path("beneId") String beneficiaryId, @Path("amount") String amount, @Path("otp") String otp, @Path("latitude") String latitude, @Path("longitude") String longitude);



    /**
     * LUDO TOURNAMNET
     **/
    @GET(AppConstant.API_LUDO_ALL_TOURNAMENT)
    Observable<LudoTmtAllTournamentMainResponse> onGetLudoTmtAllTournament(@Query("startDate") boolean startDate, @Query("type") int type, @Query("banners") boolean banners, @Query("banner_type") String banner_type, @Query("profile") boolean profile);

    @GET(AppConstant.API_LUDO_JOIN_TOURNAMENT)
    Observable<LudoTmtJoinMainResponse> onJoinLudoTournament(@Path("id") String tournament_id);

    @GET(AppConstant.API_LUDO_PAST_TOURNAMENT)
    Observable<LudoTmtMyMatchMainResponse> onPastLudoTournament();

    @GET(AppConstant.API_LUDO_LIVE_TOURNAMENT)
    Observable<LudoTmtMyMatchMainResponse> onLiveLudoTournament();

    @GET(AppConstant.API_LUDO_UPCOMING_TOURNAMENT)
    Observable<LudoTmtMyMatchMainResponse> onUpcomingLudoTournament();

    @GET(AppConstant.API_LUDO_TOURNAMENT_ROUNDS)
    Observable<LudoTmtRoundsDetailsMainResponse> onLudoTournamentRound(@Path("id") String tournament_id);

    @GET(AppConstant.API_LUDO_TOURNAMENT_PAST_ROUNDS)
    Observable<LudoTmtAllPastRoundsMainResponse> onLudoTournamentPastRound(@Path("id") String tournament_id);



    //GamerCashGetData
    @GET(AppConstant.API_FETCH_GAMER_CASH)
    Observable<GetGamerCashResponse> payGamerCashData();

    //GamerCashVerifying
    @GET(AppConstant.API_GAMER_CASH_VERFIYUSER)
    Observable<GamerCashResponse> getGamerCashData();

    //GamerCash Switch Coins
    @POST(AppConstant.API_SWITCH_GAMER_CASH)
    Observable<SwitchGamerCashResponse> switchGamerData(@Body SwitchGamerCashRequest switchGamerCashRequest);

    //Rummy Get List
    @GET(AppConstant.API_GET_RUMMY_LIST)
    Observable<RummyResponse> getRummyList(@Query("arenaType") String arenaType, @Query("bannerType") String bannerType);

    @GET(AppConstant.API_GET_RUMMY_REFRESH_TOKEN)
    Observable<RummyRefreshTokenMainResponse> getRummyRefershToken();

    @GET(AppConstant.API_RUMMY_CHECKGAMESTATUS)
    Observable<RummyCheckGameResponse> getRummyCheckGameStatus();

    @GET(AppConstant.API_RUMMY_HISTORY)
    Observable<RummyHistoryMainResponse> getRummyHistory();

    //Call Break Get List
    @GET(AppConstant.API_GET_CALLBREAK_LIST)
    Observable<CallBreakResponse> getCallBreak();
    @GET(AppConstant.API_GET_CALLBREAK_JOIN)
    Observable<CallBreakJoinMainResponse> getCallBreakJoin(@Path("id") String id);

    @GET(AppConstant.API_GET_CALLBREAK_HISTORY_LIST)
    Observable<CallBreakResponse> getCallBreakHistory();
    @GET(AppConstant.API_GET_CALLBREAK_HISTORY_RANK)
    Observable<CbHistoryRankMainResponse> getCallBreakHistoryRank(@Path("id") String id);

    //New Added
    @POST(AppConstant.API_PAYMENT_URL)
    Observable<PhonePePaymentResponse> getPaymentUrl(@Body() PhonepeRequest phonepeRequest);

    @POST(AppConstant.API_CHECK_PAYMENT_SUCCESS)
    Observable<PhonepeCheckPaymentResponse> getCheckPaymentSuccess(@Body() PhonepeCheckPaymentRequest phonepeCheckPaymentRequest);

    //Cashfree Status
    @GET(AppConstant.API_CASHFREE_STATUS)
    Observable<BaseResponse> getCashfreeStatus(@Path("orderId") String orderId);

    @GET(AppConstant.API_CX_BANNER)
    Observable<CxBannerMainResponse> getCxBanner(@Query("type") String type);

    @GET(AppConstant.API_CHECK_TDS)
    Observable<TdsResponse> onCheckTDS(@Query("amount") int amount);

    @POST("v1/wrapper/generate/otp")
    Observable<BajajPayResponse> forBajajPayOTP(@Header("Content-Type") String content_type, @Header("channel") String channel, @Header("Ocp-Apim-Subscription-Key") String Ocp_Api_sub_key, @Header("Ocp-Apim-Trace") boolean Ocp_api_trace, @Body BajajPayEncryptedRequest req);

    @POST("v1/wrapper/resend/otp")
    Observable<BajajPayResponse> forBajajPayResendOTP(@Header("Content-Type") String content_type, @Header("channel") String channel, @Header("Ocp-Apim-Subscription-Key") String Ocp_Api_sub_key, @Header("Ocp-Apim-Trace") boolean Ocp_api_trace, @Body BajajPayEncryptedRequest bajajPayEncryptedRequest);

    @POST("v1/wrapper/verify/otp")
    Observable<BajajPayVerifyOtpResponse> forBajajPayVerifyOTP(@Header("Content-Type") String content_type, @Header("channel") String channel, @Header("Ocp-Apim-Subscription-Key") String Ocp_Api_sub_key, @Header("Ocp-Apim-Trace") boolean Ocp_api_trace, @Body BajajPayEncryptedRequest payEncryptedRequest);

    @POST("v1/wrapper/wallet/balance")
    Observable<BajajPayGetBalanceResponse> getBajajBalance(@Header("Content-Type") String content_type, @Header("channel") String channel, @Header("Ocp-Apim-Subscription-Key") String Ocp_Api_sub_key, @Header("Ocp-Apim-Trace") boolean Ocp_api_trace, @Body BajajPayEncryptedRequest payEncryptedRequest);

    @POST("v1/wrapper/payment/auth")
    Observable<BajajPayResponse> getPayment(@Header("Content-Type") String content_type, @Header("channel") String channel, @Header("Ocp-Apim-Subscription-Key") String Ocp_Api_sub_key, @Header("Ocp-Apim-Trace") boolean Ocp_api_trace, @Body BajajPayEncryptedRequest payEncryptedRequest);

    @POST("v1/wrapper/debit/transaction")
    Observable<BajajPayResponse> getBajajPayDebitPayment(@Header("Content-Type") String content_type, @Header("channel") String channel, @Header("Ocp-Apim-Subscription-Key") String Ocp_Api_sub_key, @Header("Ocp-Apim-Trace") boolean Ocp_api_trace, @Body BajajPayEncryptedRequest bajajPayDebitTransactionRequest);

    @POST("v1/wrapper/payment/add-money")
    Observable<BajajPayResponse> getBajajPayAddMoneyBalance(@Header("Content-Type") String content_type, @Header("channel") String channel, @Header("Ocp-Apim-Subscription-Key") String Ocp_Api_sub_key, @Header("Ocp-Apim-Trace") boolean Ocp_api_trace, @Body BajajPayEncryptedRequest mBajajPayInsufficientBalanceDebitListener);

    @POST("v1/wrapper/user/delink")
    Observable<BajajPayResponse> delinkWallet(@Header("Content-Type") String content_type, @Header("channel") String channel, @Header("Ocp-Apim-Subscription-Key") String Ocp_Api_sub_key, @Header("Ocp-Apim-Trace") boolean Ocp_api_trace, @Body BajajPayEncryptedRequest bajajPayDeLinkWalletRequest);

    @POST(AppConstant.UPDATE_BALANCE)
    Observable<UpdateBalanceResponse> updateBalance(@Body UpdateBalanceRequest updateBalanceRequest);

}
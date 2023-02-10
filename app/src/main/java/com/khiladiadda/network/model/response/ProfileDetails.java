package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.request.GameCredential;

import java.util.List;

public class ProfileDetails {

    @SerializedName("coins") @Expose private Coins coins;
    @SerializedName("n_quiz") @Expose private NQuiz nQuiz;
    @SerializedName("n_game") @Expose private NGame nGame;
    @SerializedName("n_ludo") @Expose private NLudo nLudo;
    @SerializedName("n_ludoadda") @Expose private NSnake nSnake;
    @SerializedName("n_cof") @Expose private NClashX nClashOfFans;
    @SerializedName("n_fan_battle") @Expose private NFanBattle nFanBattle;
    @SerializedName("n_wordsearch") @Expose private NWordSearch nWordSearch;
    @SerializedName("n_gKite") @Expose private NGKITE nGKite;
    @SerializedName("n_ludoTournament") @Expose private NLudoTournament nLudoTournament;
    @SerializedName("n_rummy") @Expose private NRummy nRummy;
    @SerializedName("n_callBreak") @Expose private NCallBreak nCallBreak;
    @SerializedName("username") @Expose private String username;
    @SerializedName("dp") @Expose private String dp;
    @SerializedName("pan") @Expose private String pan;
    @SerializedName("dob") @Expose private String dob;
    @SerializedName("state") @Expose private String state;
    @SerializedName("country") @Expose private String country;
    @SerializedName("gender") @Expose private String gender;
    @SerializedName("invitecode") @Expose private String invitecode;
    @SerializedName("otp_verified") @Expose private Boolean otpVerified;
    @SerializedName("is_blocked") @Expose private Boolean isBlocked;
    @SerializedName("entry_fee_spent") @Expose private double entryFeeSpent;
    @SerializedName("quiz_milestone_level") @Expose private int quizMilestoneLevel;
    @SerializedName("credentials") @Expose private List<GameCredential> credentials = null;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("mobile") @Expose private long mobile;
    @SerializedName("email") @Expose private String email;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("is_pan_updated") @Expose private int panUpdated;
    @SerializedName("pan_info") @Expose private PanDetails panDetails;
    @SerializedName("is_aadhar_updated") @Expose private int aadharUpdated;
    @SerializedName("aadhar_info") @Expose private PanDetails aadharDetails;
    @SerializedName("address") @Expose private String address;
    @SerializedName("email_verified") @Expose private boolean emailVerified;

    public NQuiz getnQuiz() {
        return nQuiz;
    }

    public void setnQuiz(NQuiz nQuiz) {
        this.nQuiz = nQuiz;
    }

    public NGame getnGame() {
        return nGame;
    }

    public void setnGame(NGame nGame) {
        this.nGame = nGame;
    }

    public NLudoTournament getnLudoTournament() {
        return nLudoTournament;
    }

    public void setnLudoTournament(NLudoTournament nLudoTournament) {
        this.nLudoTournament = nLudoTournament;
    }

    public NRummy getnRummy() {
        return nRummy;
    }

    public void setnRummy(NRummy nRummy) {
        this.nRummy = nRummy;
    }

    public NCallBreak getnCallBreak() {
        return nCallBreak;
    }

    public void setnCallBreak(NCallBreak nCallBreak) {
        this.nCallBreak = nCallBreak;
    }

    public int getPanUpdated() {
        return panUpdated;
    }

    public void setPanUpdated(int panUpdated) {
        this.panUpdated = panUpdated;
    }

    public Coins getCoins() {
        return coins;
    }

    public void setCoins(Coins coins) {
        this.coins = coins;
    }

    public NQuiz getNQuiz() {
        return nQuiz;
    }

    public void setNQuiz(NQuiz nQuiz) {
        this.nQuiz = nQuiz;
    }

    public NGame getNGame() {
        return nGame;
    }

    public void setNGame(NGame nGame) {
        this.nGame = nGame;
    }

    public NLudo getnLudo() {
        return nLudo;
    }

    public void setnLudo(NLudo nLudo) {
        this.nLudo = nLudo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }

    public Boolean getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(Boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    public double getEntryFeeSpent() {
        return entryFeeSpent;
    }

    public void setEntryFeeSpent(double entryFeeSpent) {
        this.entryFeeSpent = entryFeeSpent;
    }

    public int getQuizMilestoneLevel() {
        return quizMilestoneLevel;
    }

    public void setQuizMilestoneLevel(int quizMilestoneLevel) {
        this.quizMilestoneLevel = quizMilestoneLevel;
    }

    public List<GameCredential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<GameCredential> credentials) {
        this.credentials = credentials;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public NSnake getnSnake() {
        return nSnake;
    }

    public void setnSnake(NSnake nSnake) {
        this.nSnake = nSnake;
    }

    public PanDetails getPanDetails() {
        return panDetails;
    }

    public void setPanDetails(PanDetails panDetails) {
        this.panDetails = panDetails;
    }

    public int getAadharUpdated() {
        return aadharUpdated;
    }

    public void setAadharUpdated(int aadharUpdated) {
        this.aadharUpdated = aadharUpdated;
    }

    public PanDetails getAadharDetails() {
        return aadharDetails;
    }

    public void setAadharDetails(PanDetails aadharDetails) {
        this.aadharDetails = aadharDetails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public NClashX getnClashOfFans() {
        return nClashOfFans;
    }

    public void setnClashOfFans(NClashX nClashOfFans) {
        this.nClashOfFans = nClashOfFans;
    }

    public NFanBattle getnFanBattle() {
        return nFanBattle;
    }

    public void setnFanBattle(NFanBattle nFanBattle) {
        this.nFanBattle = nFanBattle;
    }

    public NWordSearch getnWordSearch() {
        return nWordSearch;
    }

    public void setnWordSearch(NWordSearch nWordSearch) {
        this.nWordSearch = nWordSearch;
    }

    public NGKITE getnGKite() {
        return nGKite;
    }

    public void setnGKite(NGKITE nGKite) {
        this.nGKite = nGKite;
    }
}
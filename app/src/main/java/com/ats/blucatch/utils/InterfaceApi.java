package com.ats.blucatch.utils;

import com.ats.blucatch.bean.Account;
import com.ats.blucatch.bean.AccountData;
import com.ats.blucatch.bean.AccountListData;
import com.ats.blucatch.bean.AddDevices;
import com.ats.blucatch.bean.AllSpinnerDataForTrip;
import com.ats.blucatch.bean.Boat;
import com.ats.blucatch.bean.BoatData;
import com.ats.blucatch.bean.BoatWiseExpenseData;
import com.ats.blucatch.bean.ErrorMessage;
import com.ats.blucatch.bean.Expense;
import com.ats.blucatch.bean.ExpenseApproveData;
import com.ats.blucatch.bean.ExpensesData;
import com.ats.blucatch.bean.Fish;
import com.ats.blucatch.bean.FishCatch;
import com.ats.blucatch.bean.FishData;
import com.ats.blucatch.bean.FishSellList;
import com.ats.blucatch.bean.HomeCountData;
import com.ats.blucatch.bean.LedgerData;
import com.ats.blucatch.bean.Login;
import com.ats.blucatch.bean.NotificationBean;
import com.ats.blucatch.bean.NotificationData;
import com.ats.blucatch.bean.Season;
import com.ats.blucatch.bean.SeasonData;
import com.ats.blucatch.bean.Transaction;
import com.ats.blucatch.bean.TransactionAccountData;
import com.ats.blucatch.bean.Trip;
import com.ats.blucatch.bean.TripData;
import com.ats.blucatch.bean.TripExpensesListData;
import com.ats.blucatch.bean.TripSettleTransactions;
import com.ats.blucatch.bean.User;
import com.ats.blucatch.bean.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by maxadmin on 7/9/17.
 */

public interface InterfaceApi {

    //public static final String URL = "http://blucatch.ap-south-1.elasticbeanstalk.com/";
    public static final String URL = "http://192.168.2.9:8075/";
    //public static final String URL = "http://192.168.1.104:8098/";

    public static final String MY_PREF = "BluCatch";

    @GET("login")
    Call<Login> doLogin(@Query("username") String email, @Query("password") String password);

    @GET("getAllAccountData")
    Call<AccountData> allAccountData();

    @POST("insertAccount")
    Call<ErrorMessage> addAccount(@Body Account account);

    @POST("editAccount")
    Call<ErrorMessage> editAccount(@Query("accId") long accId, @Body Account account);

    @POST("deleteAccount")
    Call<ErrorMessage> deleteAccount(@Query("accId") long accId);

    @POST("insertUser")
    Call<ErrorMessage> addUser(@Body User user);

    @GET("getAllUserData")
    Call<UserData> allUserData();

    @POST("editUser")
    Call<ErrorMessage> editUser(@Query("userId") int userId, @Body User user);

    @POST("deleteUser")
    Call<ErrorMessage> deleteUser(@Query("userId") int userId);

    @POST("blockUnblockUser")
    Call<ErrorMessage> blockUnblockUser(@Query("userId") int userId);

    @POST("insertFish")
    Call<ErrorMessage> addFish(@Body Fish fish);

    @POST("editFish")
    Call<ErrorMessage> editFish(@Query("fishId") int fishId, @Body Fish fish);

    @GET("getAllFishData")
    Call<FishData> allFishData();

    @POST("deleteFish")
    Call<ErrorMessage> deleteFish(@Query("fishId") int fishId);

    @POST("insertBoat")
    Call<ErrorMessage> addBoat(@Body Boat boat);

    @GET("getAllBoatData")
    Call<BoatData> allBoatData();

    @POST("editBoat")
    Call<ErrorMessage> editBoat(@Query("boatId") long boatId, @Body Boat boat);

    @POST("deleteBoat")
    Call<ErrorMessage> deleteBoat(@Query("boatId") long boatId);

    @POST("blockUnblockBoat")
    Call<ErrorMessage> blockUnblockBoat(@Query("boatId") long boatId);

    @GET("getAllTripData")
    Call<TripData> allTripData();

    @GET("getAllTripDataBySeason")
    Call<TripData> allTripDataBySeason(@Query("coId") int coId);

    @POST("insertTrip")
    Call<ErrorMessage> addTrip(@Body Trip trip);

    @POST("editTrip")
    Call<ErrorMessage> editTrip(@Query("tripId") long tripId, @Body Trip trip);

    @POST("deleteTrip")
    Call<ErrorMessage> deleteTrip(@Query("tripId") long tripId);

    @GET("getAllExpensesData")
    Call<ExpensesData> allExpenseData();

    @POST("insertExpenses")
    Call<ErrorMessage> addExpenses(@Body Expense expense);

    @POST("editExpenses")
    Call<ErrorMessage> editExpenses(@Query("expId") long expId, @Body Expense expense);

    @POST("deleteExpenses")
    Call<ErrorMessage> deleteExpenses(@Query("expId") long expId);

    @GET("getAllTripDataByTandelId")
    Call<TripData> allTripDataByTandel(@Query("tandelId") long tandelId);

    @GET("getAllTripDataByTandelIdAndSeason")
    Call<TripData> allTripDataByTandelAndSeason(@Query("tandelId") long tandelId, @Query("coId") int coId);

    @GET("getAllTripDataByManagerId")
    Call<TripData> allTripDataByManager(@Query("managerId") long managerId);

    @GET("getAllTripDataByManagerIdAndSeason")
    Call<TripData> allTripDataByManagerAndSeason(@Query("managerId") long managerId, @Query("coId") int coId);

    @GET("getAllTripDataByAuctionerId")
    Call<TripData> allTripDataByAuctioner(@Query("auctionerId") long auctionerId);

    @GET("getAllTripDataByAuctionerIdAndSeason")
    Call<TripData> allTripDataByAuctionerAndSeason(@Query("auctionerId") long auctionerId, @Query("coId") int coId);

    @POST("changePasswordUser")
    Call<ErrorMessage> changePassword(@Query("userId") int userId, @Query("userOldPass") String oldPass, @Query("userNewPass") String newPass);

    @GET("getAllAccountDataForTransaction")
    Call<AccountListData> allAccountListForTransaction();

    @POST("insertTransaction")
    Call<ErrorMessage> addNewTransaction(@Body Transaction transaction);

    @GET("getTransactionEntryByAccId")
    Call<LedgerData> allLedgerDataByAccId(@Query("cr") long cr, @Query("dr") long dr);

    @GET("getExpensesForApprove")
    Call<ExpenseApproveData> allExpensesForApprove();

    @POST("approveExpense")
    Call<ErrorMessage> approveExpense(@Query("trId") int trId, @Query("remark") String remark, @Query("userId") int userId);

    @POST("rejectExpense")
    Call<ErrorMessage> rejectExpense(@Query("trId") int trId, @Query("remark") String remark, @Query("userId") int userId);

    @GET("getAllSpinnerDataForTrip")
    Call<AllSpinnerDataForTrip> allSpinnerDataForTrip();

    @GET("getTripWiseAccountList")
    Call<TransactionAccountData> allAccountTripWise(@Query("tripId") long tripId, @Query("type") String type);

    @GET("getTripWiseExpenses")
    Call<TripExpensesListData> allTripWiseExpenses(@Query("tripId") long tripId);

    @GET("getBoatWiseAccountList")
    Call<TransactionAccountData> allAccountBoatWise(@Query("boatId") long boatId);

    @GET("getAccountListForTripSettle")
    Call<TransactionAccountData> allAccountForTripSettle(@Query("tripId") long tripId);

    @POST("insertFishCatch")
    Call<ErrorMessage> addFishCatch(@Body ArrayList<FishCatch> fitchCatch);

    @GET("getFishSellList")
    Call<FishSellList> getFishSellList(@Query("tripId") long tripId);

    @POST("insertSeason")
    Call<ErrorMessage> addSeason(@Body Season season);

    @GET("getAllSeasonData")
    Call<SeasonData> getAllSeason(@Query("coId") int coId);

    @GET("getAllTripDataByBoatId")
    Call<TripData> allTripDataByBoat(@Query("boatId") long boatId);

    @GET("getAllExpensesForBoat")
    Call<BoatWiseExpenseData> allBoatExpenses(@Query("boatId") long boatId);

    @POST("insertTripSettleTransaction")
    Call<ErrorMessage> tripSettle(@Query("tripId") long tripId, @Body ArrayList<TripSettleTransactions> tripSettleTransactionses);

    @POST("insertDeviceToken")
    Call<ErrorMessage> addDeviceToken(@Body AddDevices addDevices);

    @POST("insertNotification")
    Call<ErrorMessage> addNotification(@Body NotificationBean notification);

    @GET("getAllNotification")
    Call<NotificationData> getNotificationData(@Query("coId") int coId);

    @GET("getAllHomeCount")
    Call<HomeCountData> getHomeCount(@Query("coId") int coId);

    @POST("seasonClose")
    Call<ErrorMessage> seasonEnd(@Query("seasonId") int seasonId);
}

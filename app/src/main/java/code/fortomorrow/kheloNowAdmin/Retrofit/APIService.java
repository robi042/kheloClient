package code.fortomorrow.kheloNowAdmin.Retrofit;


import code.fortomorrow.kheloNowAdmin.Model.AddMoneyResponse.AddMoneyResponse;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_of_valor_match_response;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_result_match_info;
import code.fortomorrow.kheloNowAdmin.Model.CheckJoinTeam.Check_join_team_response;
import code.fortomorrow.kheloNowAdmin.Model.GetMatch.FreeFire_paginated_live_response;
import code.fortomorrow.kheloNowAdmin.Model.GetMatch.GetMatchResponse;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.Free_fire_paginated_result_response;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.GetResultResponse;
import code.fortomorrow.kheloNowAdmin.Model.GetTokenResponse.ResponseMessage;
import code.fortomorrow.kheloNowAdmin.Model.How_to_join_response;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.JoinedPlayerResponse;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.Model.LoginResponse.LoginResponse;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.LudoMatchParticipantList_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_full_result_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_match_statistics_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_paginated_match_list;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_result_pagination_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_rules_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_uploaded_image_response;
import code.fortomorrow.kheloNowAdmin.Model.MatchLength.Match_length_response;
import code.fortomorrow.kheloNowAdmin.Model.Message.Message_response;
import code.fortomorrow.kheloNowAdmin.Model.Notice.Notice_response;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.OngoingResponse;
import code.fortomorrow.kheloNowAdmin.Model.OrdersResponse.OrdersResponse;
import code.fortomorrow.kheloNowAdmin.Model.PopUp.Pop_up_data_response;
import code.fortomorrow.kheloNowAdmin.Model.PopUp.Pop_up_response;
import code.fortomorrow.kheloNowAdmin.Model.Products.Product_response;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.Model.RefreshToken.RefreshToken;
import code.fortomorrow.kheloNowAdmin.Model.ResultResponse.ResultResponse;
import code.fortomorrow.kheloNowAdmin.Model.Rules.GetRulesResponse;
import code.fortomorrow.kheloNowAdmin.Model.Rules.Rules_response;
import code.fortomorrow.kheloNowAdmin.Model.Slider.Slider_list_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.Statistics.StatisticsResponse;
import code.fortomorrow.kheloNowAdmin.Model.Status_response;
import code.fortomorrow.kheloNowAdmin.Model.Support.Support_numbers_response;
import code.fortomorrow.kheloNowAdmin.Model.TopPlayers.Top_players_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Transaction.TransactionResponse;
import code.fortomorrow.kheloNowAdmin.Model.UpcomingMatches.Free_fire_upcoming_match_list_response;
import code.fortomorrow.kheloNowAdmin.Model.UpcomingMatches.Ludo_upcoming_match_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Update.UpdateResponse;
import code.fortomorrow.kheloNowAdmin.Model.getNumbers.GetNumbersResponse;
import code.fortomorrow.kheloNowAdmin.Model.getRoomIdPass.GetRoomIdResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("player/update")
    Call<UpdateResponse> upDateCheck();


    @FormUrlEncoded
    @POST("player/login")
    Call<LoginResponse> getLoginResponse(@Field("phone") String phone, @Field("password") String password, @Field("auth_code") String authCode);

    @FormUrlEncoded
    @POST("player/reset_request")
    Call<SorkariResponse> getTokenForReset(@Field("phone") String phone, @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST("player/reset_password")
    Call<SorkariResponse> reset_password(@Field("token") String token, @Field("password") String password);

    @FormUrlEncoded
    @POST("player/refresh_token")
    Call<RefreshToken> getRefreshToken(@Field("secret_id") String secret_id, @Field("auth_code") String auth_code);

    @FormUrlEncoded
    @POST("player/get_all_cs_lover_match_length")
    Call<Match_length_response> getCS_all_match_length(@Field("secret_id") String secret_id,
                                                       @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_tournament_match_length")
    Call<SorkariResponse> get_scrims_match_length(@Field("secret_id") String secret_id,
                                                  @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_cs_lover_match_length")
    Call<SorkariResponse> getmatchlength_cs(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token, @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("player/get_regular_match_length")
    Call<SorkariResponse> getmatchlength_regular(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_freefire_premium_match_length")
    Call<SorkariResponse> get_freefire_premium_match_length(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_freefire_grand_match_length")
    Call<SorkariResponse> get_freefire_grand_match_length(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token);

    //get_freefire_premium_match_length

    @FormUrlEncoded
    @POST("player/created_match_list_regular")
    Call<GetMatchResponse> getRegularmatch(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token, @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("player/created_match_list_regular_one")
    Call<FreeFire_paginated_live_response> getRegularmatchOne(@Field("secret_id") String secret_id,
                                                              @Field("jwt_token") String jwt_token,
                                                              @Field("game_id") String gameID,
                                                              @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/created_match_list_regular_two")
    Call<FreeFire_paginated_live_response> getRegularmatchTwo(@Field("secret_id") String secret_id,
                                                              @Field("jwt_token") String jwt_token,
                                                              @Field("game_id") String gameID,
                                                              @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/created_match_list_cs")
    Call<GetMatchResponse> getCSmatch(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token, @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("player/created_match_list_cs_one")
    Call<FreeFire_paginated_live_response> getCSmatchOne(@Field("secret_id") String secret_id,
                                                         @Field("jwt_token") String jwt_token,
                                                         @Field("game_id") String gameID,
                                                         @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/created_match_list_cs_two")
    Call<FreeFire_paginated_live_response> getCSmatchTwo(@Field("secret_id") String secret_id,
                                                         @Field("jwt_token") String jwt_token,
                                                         @Field("game_id") String gameID,
                                                         @Field("page_number") String page_number);
    ///player/separated_cs_list_one
    ///player/separated_cs_list_two

    @FormUrlEncoded
    @POST("player/separated_cs_list_one")
    Call<FreeFire_paginated_live_response> get_separated_cs_list_one(@Field("secret_id") String secret_id,
                                                                     @Field("jwt_token") String jwt_token,
                                                                     @Field("game_id") String gameID,
                                                                     @Field("playing_type_id") String playing_type_id,
                                                                     @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/separated_cs_list_two")
    Call<FreeFire_paginated_live_response> get_separated_cs_list_two(@Field("secret_id") String secret_id,
                                                                     @Field("jwt_token") String jwt_token,
                                                                     @Field("game_id") String gameID,
                                                                     @Field("playing_type_id") String playing_type_id,
                                                                     @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/joined_player_list")
    Call<JoinedPlayerResponse> getJoinedPlayer(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token, @Field("match_id") String matchID);

    @FormUrlEncoded
    @POST("player/get_profile_info")
    Call<ProfileResponse> getProfile(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_all_tranjection")
    Call<TransactionResponse> getTransactionAll(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token);


    @FormUrlEncoded
    @POST("player/get_all_orders")
    Call<OrdersResponse> getAllOrders(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_room_info")
    Call<GetRoomIdResponse> getRoomIdPassResponse(@Field("secret_id") String secret_id,
                                                  @Field("jwt_token") String jwt_token,
                                                  @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("player/ongoing_match_list")
    Call<OngoingResponse> getOngoingResponse(@Field("secret_id") String secret_id,
                                             @Field("jwt_token") String jwt_token,
                                             @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("player/result_match_list")
    Call<GetResultResponse> getResultResponse(@Field("secret_id") String secret_id, @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/result_match_list_one")
    Call<Free_fire_paginated_result_response> getResultResponseOne(@Field("secret_id") String secret_id,
                                                                   @Field("jwt_token") String jwt_token,
                                                                   @Field("game_id") String game_id,
                                                                   @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/result_match_list_two")
    Call<Free_fire_paginated_result_response> getResultResponseTwo(@Field("secret_id") String secret_id,
                                                                   @Field("jwt_token") String jwt_token,
                                                                   @Field("game_id") String game_id,
                                                                   @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/requested_add_withdraw_money")
    Call<AddMoneyResponse> getResponseAddMoney(@Field("secret_id") String secret_id,
                                               @Field("jwt_token") String jwt_token,
                                               @Field("type") String type,
                                               @Field("amount") String amount,
                                               @Field("payment_method") String payment_method,
                                               @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("player/get_payment_number")
    Call<GetNumbersResponse> getNumbersList(@Field("secret_id") String secret_id,
                                            @Field("jwt_token") String jwt_token,
                                            @Field("type") String type);

    @FormUrlEncoded
    @POST("player/get_result_match_info")
    Call<ResultResponse> getResults(@Field("secret_id") String secret_id,
                                    @Field("jwt_token") String jwt_token,
                                    @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("player/get_statastic_matches")
    Call<StatisticsResponse> getStatistics(@Field("secret_id") String secret_id,
                                           @Field("jwt_token") String jwt_token);

    //tournament_statastic_info

    @FormUrlEncoded
    @POST("player/tournament_statastic_info")
    Call<StatisticsResponse> getScrimsStatistics(@Field("secret_id") String secret_id,
                                                 @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/show_rules")
    Call<GetRulesResponse> getRules(@Field("secret_id") String secret_id,
                                    @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_fireefire_rules")
    Call<GetRulesResponse> getFreeFireRules(@Field("secret_id") String secret_id,
                                            @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_tournament_rules")
    Call<GetRulesResponse> getScrimsRules(@Field("secret_id") String secret_id,
                                          @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/match_entry")
    Call<SorkariResponse> getJoinMatchResponse(@Field("secret_id") String secret_id,
                                               @Field("jwt_token") String jwt_token,
                                               @Field("match_id") String match_id,
                                               @Field("first_player") String first_player,
                                               @Field("second_player") String second_player,
                                               @Field("third_player") String third_player,
                                               @Field("forth_player") String forth_player);

    @FormUrlEncoded
    @POST("player/get_token")
    Call<ResponseMessage> getToken(@Field("phone") String phone_number,
                                   @Field("user_name") String usernmae,
                                   @Field("deviceId") String deviceId);


    @FormUrlEncoded
    @POST("player/regitration")
    Call<ResponseMessage> registration(@Field("name") String userName,
                                       @Field("email") String email,
                                       @Field("phone") String mobile,
                                       @Field("password") String password,
                                       @Field("user_name") String username,
                                       @Field("promo") String promo,
                                       @Field("refer") String refer,
                                       @Field("token") String token);

    @FormUrlEncoded
    @POST("player/get_created_ludo_match")
    Call<Ludo_paginated_match_list> getLudoTournamentGameList(@Field("secret_id") String secret_id,
                                                              @Field("jwt_token") String jwt_token,
                                                              @Field("game_id") String gameID,
                                                              @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/get_ongoing_ludo_match")
    Call<Ludo_tournament_game_list_response> getOngoingLudoMatchList(@Field("secret_id") String secret_id,
                                                                     @Field("jwt_token") String jwt_token,
                                                                     @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("player/get_ludo_match_length")
    Call<Match_length_response> getLudoMatchLength(@Field("secret_id") String secret_id,
                                                   @Field("jwt_token") String jwt_token,
                                                   @Field("game_id") String gameID);


    @FormUrlEncoded
    @POST("player/get_all_ludo_match_length")
    Call<Match_length_response> getTotalLudoMatchLength(@Field("secret_id") String secret_id,
                                                        @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/join_ludo_match")
    Call<SorkariResponse> joinLudoMatch(@Field("secret_id") String secret_id,
                                        @Field("jwt_token") String jwt_token,
                                        @Field("match_id") String matchID,
                                        @Field("first_player") String first_player);

    @FormUrlEncoded
    @POST("player/get_ludo_match_join_list")
    Call<LudoMatchParticipantList_response> joinLudoMatchParticipantList(@Field("secret_id") String secret_id,
                                                                         @Field("jwt_token") String jwt_token,
                                                                         @Field("match_id") String matchID);


    @FormUrlEncoded
    @POST("player/get_result_ludo_match")
    Call<Ludo_result_pagination_response> getResultLudoMatchList(@Field("secret_id") String secret_id,
                                                                 @Field("jwt_token") String jwt_token,
                                                                 @Field("game_id") String gameID,
                                                                 @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/get_ludo_rules")
    Call<Ludo_rules_response> getLudoRules(@Field("secret_id") String secret_id,
                                           @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/ludo_result_info")
    Call<Ludo_full_result_response> LudoFullResultList(@Field("secret_id") String secret_id,
                                                       @Field("jwt_token") String jwt_token,
                                                       @Field("match_id") String matchID);

    @FormUrlEncoded
    @POST("player/upload_image")
    Call<SorkariResponse> uploadImage(@Field("secret_id") String secret_id,
                                      @Field("jwt_token") String jwt_token,
                                      @Field("match_id") String matchID,
                                      @Field("image") String image);

    //player/ludo_image_upload
    @FormUrlEncoded
    @POST("player/ludo_image_upload")
    Call<SorkariResponse> ludoImageUpload(@Field("secret_id") String secret_id,
                                          @Field("jwt_token") String jwt_token,
                                          @Field("match_id") String matchID,
                                          @Field("image") String image);


    @FormUrlEncoded
    @POST("player/get_ludo_image")
    Call<Ludo_uploaded_image_response> getLudoImage(@Field("secret_id") String secret_id,
                                                    @Field("jwt_token") String jwt_token,
                                                    @Field("match_id") String matchID);

    @FormUrlEncoded
    @POST("player/get_youtube_link")
    Call<How_to_join_response> getHowToJoin(@Field("secret_id") String secret_id,
                                            @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/ludo_match_statastic")
    Call<Ludo_match_statistics_response> getLudoStatistics(@Field("secret_id") String secret_id,
                                                           @Field("jwt_token") String jwt_token);


    @FormUrlEncoded
    @POST("player/get_user_status")
    Call<Status_response> getStatus(@Field("secret_id") String secret_id,
                                    @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_slider_list")
    Call<Slider_list_response> getSliderList(@Field("secret_id") String secret_id,
                                             @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_popup_status")
    Call<Pop_up_response> getPopUpResponse(@Field("secret_id") String secret_id,
                                           @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_popup")
    Call<Pop_up_data_response> getPopUpData(@Field("secret_id") String secret_id,
                                            @Field("jwt_token") String jwt_token);

    //matches for testing
    @FormUrlEncoded
    @POST("player/created_match_list")
    Call<GetMatchResponse> getCreatedTestMatchList(@Field("secret_id") String secret_id,
                                                   @Field("jwt_token") String jwt_token,
                                                   @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("player/get_created_tournament_match_list")
    Call<FreeFire_paginated_live_response> getCreatedScrimsMatchList(@Field("secret_id") String secret_id,
                                                                     @Field("jwt_token") String jwt_token,
                                                                     @Field("game_id") String gameID,
                                                                     @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/match_new_entry")
    Call<SorkariResponse> newMatchEntryResponse(@Field("secret_id") String secret_id,
                                                @Field("jwt_token") String jwt_token,
                                                @Field("match_id") String match_id,
                                                @Field("team_no") String team_no,
                                                @Field("hasFirstPlayer") String hasFirstPlayer,
                                                @Field("first_player") String first_player,
                                                @Field("hasSecondPlayer") String hasSecondPlayer,
                                                @Field("second_player") String second_player,
                                                @Field("hasThirdPlayer") String hasThirdPlayer,
                                                @Field("third_player") String third_player,
                                                @Field("hasForthPlayer") String hasForthPlayer,
                                                @Field("forth_player") String forth_player,
                                                @Field("hasFifthPlayer") String hasFifthPlayer,
                                                @Field("fifth_player") String fifth_player,
                                                @Field("hasSixthPlayer") String hasSixthPlayer,
                                                @Field("sixth_player") String sixth_player,
                                                @Field("hasExtraPlayerOne") String hasExtraPlayerOne,
                                                @Field("extraPlayerOne") String extraPlayerOne,
                                                @Field("hasExtraPlayerTwo") String hasExtraPlayerTwo,
                                                @Field("extraPlayerTwo") String extraPlayerTwo);

    //scrims
    @FormUrlEncoded
    @POST("player/tournament_entry")
    Call<SorkariResponse> newScrimsMatchEntryResponse(@Field("secret_id") String secret_id,
                                                      @Field("jwt_token") String jwt_token,
                                                      @Field("match_id") String match_id,
                                                      @Field("hasFirstPlayer") String hasFirstPlayer,
                                                      @Field("hasSecondPlayer") String hasSecondPlayer,
                                                      @Field("hasThirdPlayer") String hasThirdPlayer,
                                                      @Field("hasForthPlayer") String hasForthPlayer,
                                                      @Field("first_player") String first_player,
                                                      @Field("second_player") String second_player,
                                                      @Field("third_player") String third_player,
                                                      @Field("forth_player") String forth_player,
                                                      @Field("hasExtraOne") String hasExtraOne,
                                                      @Field("extra_player_one") String extra_player_one,
                                                      @Field("hasExtraTwo") String hasExtraTwo,
                                                      @Field("extra_player_two") String extra_player_two);


    @FormUrlEncoded
    @POST("player/get_joined_list")
    Call<Joined_player_list_response> getJoinedList(@Field("secret_id") String secret_id,
                                                    @Field("jwt_token") String jwt_token,
                                                    @Field("match_id") String match_id);


    @FormUrlEncoded
    @POST("player/get_notice")
    Call<Notice_response> getNotice(@Field("secret_id") String secret_id,
                                    @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_all_support_numbers")
    Call<Support_numbers_response> getSupportNumbers(@Field("secret_id") String secret_id,
                                                     @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_top_players_list")
    Call<Top_players_list_response> getTopPlayers(@Field("secret_id") String secret_id,
                                                  @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_upcoming_match")
    Call<Free_fire_upcoming_match_list_response> getUpComingFreeFireMatchList(@Field("secret_id") String secret_id,
                                                                              @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_upcoming_ludo_match")
    Call<Ludo_upcoming_match_list_response> getUpComingLudoMatchList(@Field("secret_id") String secret_id,
                                                                     @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/get_link")
    Call<SorkariResponse> getVideoLink(@Field("secret_id") String secret_id,
                                       @Field("jwt_token") String jwt_token,
                                       @Field("type") String type);

    @FormUrlEncoded
    @POST("player/check_join_team")
    Call<Check_join_team_response> getCheckJoinTeamPlayer(@Field("secret_id") String secret_id,
                                                          @Field("jwt_token") String jwt_token,
                                                          @Field("match_id") String match_id);

    //get_joined_list
    @FormUrlEncoded
    @POST("player/get_joined_list")
    Call<Check_join_team_response> get_joined_player_list(@Field("secret_id") String secret_id,
                                                          @Field("jwt_token") String jwt_token,
                                                          @Field("match_id") String match_id);

    //tournament_join_list
    @FormUrlEncoded
    @POST("player/tournament_join_list")
    Call<Check_join_team_response> getScrimsJoinTeamPlayer(@Field("secret_id") String secret_id,
                                                           @Field("jwt_token") String jwt_token,
                                                           @Field("match_id") String match_id);

    ///player/get_all_type_rules
    @FormUrlEncoded
    @POST("player/get_all_type_rules")
    Call<Rules_response> getAllRules(@Field("secret_id") String secret_id,
                                     @Field("jwt_token") String jwt_token,
                                     @Field("type") String type);

    //get_ready
    @FormUrlEncoded
    @POST("player/get_ready")
    Call<SorkariResponse> getPlayerReady(@Field("secret_id") String secret_id,
                                         @Field("jwt_token") String jwt_token,
                                         @Field("match_id") String match_id);


    //pagination
    @FormUrlEncoded
    @POST("player/ludo_paginated_result_list")
    Call<Ludo_result_pagination_response> getPaginatedLudoResultMatchList(@Field("secret_id") String secret_id,
                                                                          @Field("jwt_token") String jwt_token,
                                                                          @Field("game_id") String gameID,
                                                                          @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/result_match_list")
    Call<Free_fire_paginated_result_response> getPaginatedFreeFireResultMatchList(@Field("secret_id") String secret_id,
                                                                                  @Field("jwt_token") String jwt_token,
                                                                                  @Field("game_id") String gameID,
                                                                                  @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/created_match_list_cs")
    Call<FreeFire_paginated_live_response> getPaginatedCSFreeFireMatchList(@Field("secret_id") String secret_id,
                                                                           @Field("jwt_token") String jwt_token,
                                                                           @Field("game_id") String gameID,
                                                                           @Field("page_number") String page_number);

    //created_match_list_regular
    @FormUrlEncoded
    @POST("player/created_match_list_regular")
    Call<FreeFire_paginated_live_response> getPaginatedRegularFreeFireMatchList(@Field("secret_id") String secret_id,
                                                                                @Field("jwt_token") String jwt_token,
                                                                                @Field("game_id") String gameID,
                                                                                @Field("page_number") String page_number);

    //getcreated_paginated_ludo_list
    @FormUrlEncoded
    @POST("player/getcreated_paginated_ludo_list")
    Call<Ludo_paginated_match_list> getPaginatedLudoMatchList(@Field("secret_id") String secret_id,
                                                              @Field("jwt_token") String jwt_token,
                                                              @Field("game_id") String gameID,
                                                              @Field("page_number") String page_number);


    ///player/buy-and-sell/get_product_list
    //secret_id, jwt_token
    @FormUrlEncoded
    @POST("player/buy-and-sell/get_product_list")
    Call<Product_response> get_product_list(@Field("secret_id") String secret_id,
                                            @Field("jwt_token") String jwt_token,
                                            @Field("type") String type);


    //update profile info secret_id,jwt_token,name,email,phone
    @FormUrlEncoded
    @POST("player/update_profile_info")
    Call<SorkariResponse> updateProfileInfo(@Field("secret_id") String secret_id,
                                            @Field("jwt_token") String jwt_token,
                                            @Field("name") String name,
                                            @Field("email") String email,
                                            @Field("phone") String phone);

    //get_full_map_length
    @FormUrlEncoded
    @POST("player/get_full_map_length")
    Call<SorkariResponse> get_full_map_length(@Field("secret_id") String secret_id,
                                              @Field("jwt_token") String jwt_token);


    //arena-of-valor/get_each_type_match_length
    //secret_id, api_token, game_id

    @FormUrlEncoded
    @POST("player/arena-of-valor/get_match_length")
    Call<Match_length_response> arena_of_valor_get_match_length(@Field("secret_id") String secret_id,
                                                                @Field("jwt_token") String jwt_token);

    @FormUrlEncoded
    @POST("player/arena-of-valor/get_each_type_match_length")
    Call<Match_length_response> arena_of_valor_get_each_type_match_length(@Field("secret_id") String secret_id,
                                                                          @Field("jwt_token") String jwt_token,
                                                                          @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("player/arena-of-valor/get_created_match_list")
    Call<Arena_of_valor_match_response> arena_of_valor_get_created_match_list(@Field("secret_id") String secret_id,
                                                                              @Field("jwt_token") String jwt_token,
                                                                              @Field("game_id") String game_id,
                                                                              @Field("page_number") String page_number);

    @FormUrlEncoded
    @POST("player/arena-of-valor/get_ongoing_match_list")
    Call<Arena_of_valor_match_response> arena_of_valor_get_ongoing_match_list(@Field("secret_id") String secret_id,
                                                                              @Field("jwt_token") String jwt_token,
                                                                              @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("player/arena-of-valor/get_result_match_list")
    Call<Arena_of_valor_match_response> arena_of_valor_get_result_match_list(@Field("secret_id") String secret_id,
                                                                             @Field("jwt_token") String jwt_token,
                                                                             @Field("game_id") String game_id,
                                                                             @Field("page_number") String page_number);

    ///player/arena-of-valor/join_player_list
    //secret_id, jwt_token, match_id
    @FormUrlEncoded
    @POST("player/arena-of-valor/join_player_list")
    Call<Check_join_team_response> arena_of_valor_join_player_list(@Field("secret_id") String secret_id,
                                                                   @Field("jwt_token") String jwt_token,
                                                                   @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("player/arena-of-valor/match_entry")
    Call<SorkariResponse> arena_of_valor_match_entry(@Field("secret_id") String secret_id,
                                                     @Field("jwt_token") String jwt_token,
                                                     @Field("match_id") String match_id,
                                                     @Field("team_no") String team_no,
                                                     @Field("hasFirstPlayer") String hasFirstPlayer,
                                                     @Field("first_player") String first_player,
                                                     @Field("hasSecondPlayer") String hasSecondPlayer,
                                                     @Field("second_player") String second_player,
                                                     @Field("hasThirdPlayer") String hasThirdPlayer,
                                                     @Field("third_player") String third_player,
                                                     @Field("hasForthPlayer") String hasForthPlayer,
                                                     @Field("forth_player") String forth_player,
                                                     @Field("hasFifthPlayer") String hasFifthPlayer,
                                                     @Field("fifth_player") String fifth_player,
                                                     @Field("extra_one") String extra_one,
                                                     @Field("extra_two") String extra_two);

    ///player/arena-of-valor/result_match_info
    //secret_id,jwt_token,match_id
    @FormUrlEncoded
    @POST("player/arena-of-valor/result_match_info")
    Call<Arena_valor_result_match_info> arena_of_valor_result_match_info(@Field("secret_id") String secret_id,
                                                                         @Field("jwt_token") String jwt_token,
                                                                         @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("player/arena-of-valor/statastic_match_info")
    Call<StatisticsResponse> arena_of_valor_statistic_match_info(@Field("secret_id") String secret_id,
                                                                 @Field("jwt_token") String jwt_token);

    ///player/separated_cs_match_length
    //secret_id, jwt_token, game_id, playing_type_id
    @FormUrlEncoded
    @POST("player/separated_cs_match_length")
    Call<Match_length_response> getSeparatedCSMatchLength(@Field("secret_id") String secret_id,
                                                          @Field("jwt_token") String jwt_token,
                                                          @Field("game_id") String gameID,
                                                          @Field("playing_type_id") String playing_type_id);


    //player/read_a_message
    @FormUrlEncoded
    @POST("player/read_a_message")
    Call<Message_response> get_messages_list(@Field("secret_id") String secret_id,
                                             @Field("jwt_token") String jwt_token);


    //testing
    //@FormUrlEncoded
    @GET("player/test")
    Call<SorkariResponse> test();
}

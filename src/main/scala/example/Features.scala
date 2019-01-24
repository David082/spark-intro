package example

/**
  * Created by yu_wei on 2018/7/26.
  */
object Features {

   val featuresList = List(
      // Hotel Dim
      "masterhotelid", "hotel_lat", "hotel_lon", "hotel_zone", "hotel_city", "hotel_star", "hotel_goldstar",
      "hotel_brand", "hotel_avg_picturerate", "hotel_avg_picturerank", "hotel_fromcitycenter", "hotel_fromairport",
      "hotel_fromtrain", "hotel_price_compare_7d", "hotel_ciireceivable_sum_7d", "hotel_city_avg_fh_price_avg_7d",
      "hotel_city_avg_order_cnt_7d", "hotel_fh_price_avg_7d", "hotel_order_cnt_7d", "hotel_pv_list_avg_7d",
      "hotel_uv_list_avg_7d", "hotel_zone_avg_ciireceivable_sum_7d", "hotel_zone_avg_order_cnt_7d",
      "hotel_zone_avg_pv_list_avg_7d", "hotel_zone_avg_fh_price_avg_7d", "hotel_zone_avg_uv_list_avg_7d",
      "hotel_ciiquantity_sum_7d", "hotel_ciireceivable_sum_31d", "hotel_city_avg_fh_price_avg_31d",
      "hotel_city_avg_order_cnt_31d", "hotel_fh_price_avg_31d", "hotel_order_cnt_31d", "hotel_pv_list_avg_31d",
      "hotel_uv_list_avg_31d", "hotel_zone_avg_fh_price_avg_31d", "hotel_zone_avg_order_cnt_31d",
      "hotel_zone_avg_uv_list_avg_31d", "hotel_zone_htl_cnt", "hotel_city_htl_cnt", "hotel_novoters", "hotel_score",
      "hotel_var_tag_108", "hotel_var_tag_109", "hotel_var_tag_110", "hotel_var_tag_114", "hotel_var_tag_117",
      "hotel_tag_10_20", "hotel_tag_20_30", "hotel_tag_30_40", "hotel_tag_40_50", "hotel_tag_50_60", "hotel_tag_60_on",
      "hotel_tag_male", "hotel_tag_xiuxiandujia_r", "user_advanceddate",
      // User_Dim
      "user_avgstar_total", "user_avggoldstar", "user_numorderholidayratio", "user_avgdealpriceholiday",
      "user_numorderworkdayratio", "user_avgdealpriceworkday", "user_numinvoiceratio", "user_numcommentratio",
      "user_avgprice_total", "user_maxprice_total", "user_minprice_total", "user_stdprice", "user_cvprice",
      "user_businessorderratio", "user_substitudeorderratio", "user_ordernum", "user_citynum",
      "user_cancellordernumratio", "user_activation", "user_generous_value", "user_consume_value",
      "user_maxprice_1week", "user_avgprice_1week", "user_avgprice_3month", "user_minprice_3month",
      "user_maxprice_3month", "user_medprice_3month", "user_avghoteleval_3month", "user_avgprice_6month",
      "user_avghoteleval_6month", "user_maxhoteleval_6month", "user_minprice_6month", "user_maxprice_6month",
      "user_ordnum_1year", "user_avgstar_1year", "user_avgprice_1year", "user_minprice_1year", "user_maxprice_1year",
      "user_medprice_1year", "user_avghoteleval_1year", "user_maxhoteleval_1year", "user_minhoteleval_1year",
      "user_avgcustomereval_1year", "user_varistar_total", "user_avgcustomereval_total", "user_avghoteleval_total",
      "user_medprice_total", "user_star_low", "user_star_high", "user_price_down", "user_price_up",
      "user_interest_gps_lon", "user_interest_gps_lat", "user_tag_if_male", "user_tag_age_low", "user_tag_age_high",
      "user_tag_star_2", "user_tag_star_3", "user_tag_star_4", "user_tag_star_5", "user_tag_if_meishi",
      "user_tag_if_zijia", "user_tag_if_chongwu", "user_tag_if_minsu", "user_tag_if_brand", "user_tag_brand_1",
      "user_tag_brand_2", "user_tag_brand_3", "user_tag_if_shangwu", "user_tag_if_xiuxian", "user_tag_if_qinzi",
      "user_tag_if_compare", "if_brand_01", "if_brand_02", "if_brand_03",
      // Filter Item
      "if_star_same", "scene_poi", "scene_foreign", "scene_keyword", "user_price_lower_pres", "user_price_upper_pres",
      "user_is_2star_pres", "user_is_3star_pres", "user_is_4star_pres", "user_is_5star_pres", "user_is_brand_pres",
      "user_is_dist_pres", "user_is_4_5_score_pres", "user_is_4_0_score_pres", "user_is_3_5_score_pres",
      "user_is_3_0_score_pres", "user_is_more5_commentq_pres", "user_is_more10_commentq_pres",
      "user_is_more20_commentq_pres", "user_is_more50_commentq_pres",
      // Product Item
      "fh_price", "dist", "hotel_mainscore", "arrival_weekday", "advanced_days", "stay_fordays",
      "user_interestgps_to_nowhtl_diff", "delta_price_down", "delta_price_up", "dynamic_price_down",
      "hotel_openyear_diffdays", "hotel_fitmentyear_diffdays", "usernow_to_htl_dist", "delta_avg_price",
      "delta_hotel_city_price", "delta_hotel_self_price", "delta_hotel_zone_price",
      // Click Dim
      "user_hisclickhtl_num", "user_hisclickhtl_startprice_min", "user_hisclickhtl_startprice_max",
      "user_hisclickhtl_startprice_median", "user_hisclickhtl_star_min", "user_hisclickhtl_star_max",
      "user_hisclickhtl_star_mean", "user_hisclickhtl_to_qidhtl_dist_min", "user_hisclickhtl_to_qidhtl_dist_max",
      "user_hisclickhtl_to_qidhtl_dist_median", "user_hisclickhtl_to_qidhtl_same_ratio",
      // Collect Dim
      "user_samceity_collecthtl_to_qidhtl_sim_last1", "user_samceity_collecthtl_to_qidhtl_pricediff_last1",
      "user_samceity_collecthtl_to_qidhtl_datediff_last1", "user_samceity_collecthtl_to_qidhtl_stardiff_last1",
      "user_samceity_collecthtl_to_qidhtl_dist_last1", "user_samceity_collecthtl_to_qidhtl_sim_last2",
      "user_samceity_collecthtl_to_qidhtl_pricediff_last2", "user_samceity_collecthtl_to_qidhtl_datediff_last2",
      "user_samceity_collecthtl_to_qidhtl_stardiff_last2", "user_samceity_collecthtl_to_qidhtl_dist_last2",
      // Order Dim
      "user_samecity_hisorderhtl_to_qidhtl_sim_last1", "user_samecity_hisorderhtl_to_qidhtl_pricediff_last1",
      "user_samecity_hisorderhtl_datediff_last1", "user_samecity_hisorderhtl_to_qidhtl_stardiff_last1",
      "user_samecity_hisorderhtl_to_qidhtl_dist_last1", "user_samecity_hisorderhtl_to_qidhtl_sim_last2",
      "user_samecity_hisorderhtl_to_qidhtl_pricediff_last2", "user_samecity_hisorderhtl_datediff_last2",
      "user_samecity_hisorderhtl_to_qidhtl_stardiff_last2", "user_samecity_hisorderhtl_to_qidhtl_dist_last2",
      "user_value1", "user_value2", "user_value3", "user_value4", "user_value5", "hotel_value1", "hotel_value2",
      "hotel_value3", "hotel_value4", "hotel_value5",
      // New Add Dim
      "hotel_value6", "hotel_value7", "hotel_value8", "hotel_value9", "hotel_value10", "user_value6", "user_value7",
      "user_value8", "user_value9", "user_value10", "diff_time_last_10", "cnt_ord_browse_hot", "cnt_htl_browse_most",
      "ratio_travel_most", "ratio_travel_10", "cos_simi_qry_1_sc", "cos_simi_qry_2_sc", "cos_simi_qry_3_sc",
      "if_hotel_most", "if_hotel_hot", "user_travel_pred_score", "user_click_htl_stateprice_ratio",
      "delta_avg_price_7d", "delta_click_price", "delta_click_his_price"
   )

}

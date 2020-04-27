// ==== 封装所有的后台请求url
import {app_server} from './EnvConfig';

export const common_getProvinceList = app_server + '/common/getProvinceList';
export const common_getHelpList = app_server + '/common/getHelpList';

// 缓存
export const serverCache_getCache = app_server + '/serverCache/getCache';

// 首页
export const homeIndex_getBottomArticles = app_server + '/homeIndex/getBottomArticles';
export const homeIndex_getBottomLinks = app_server + '/homeIndex/getBottomLinks';
export const homeIndex_getNavData = app_server + '/homeIndex/getNavData';
export const homeIndex_getAllHomeFloor = app_server + '/homeIndex/getAllHomeFloor';

// 商品
export const product_detail = app_server + '/product/detail';
export const product_likeGood = app_server + '/product/likeGood';    
export const product_findPidBySpec = app_server + '/product/findPidBySpec';  
export const product_addCart = app_server + '/product/addCart';  
export const product_index = app_server + '/product/index';    
export const product_getLikePage = app_server + '/product/getLikePage';

// 用户
export const user_checkLogin = app_server + '/user/checkLogin';
export const user_logout = app_server + '/user/logout';
export const user_register = app_server + '/user/register';
export const user_loginByAccount = app_server + '/user/loginByAccount';
export const user_getImageCode = app_server + '/user/getImageCode';
export const user_getVCode4Register = app_server + '/user/getVCode4Register';
export const user_getVCode4Login = app_server + '/user/getVCode4Login';
export const user_loginByPhone = app_server + '/user/loginByPhone';
export const user_changePass = app_server + '/user/changePass';

// 购物车
export const shopcart_checkProducts = app_server + '/shopcart/checkProducts';

// 地址
export const userAddress_saveDa = app_server + '/userAddress/saveDa';
export const userAddress_getList = app_server + '/userAddress/getList';
export const userAddress_setDefault = app_server + '/userAddress/setDefault';
export const userAddress_deleteIt = app_server + '/userAddress/deleteIt';

// 订单
export const userOrder_makeOrder = app_server + '/userOrder/makeOrder';
export const userOrder_getCountdown = app_server + '/userOrder/getCountdown';
export const userOrder_getPage = app_server + '/userOrder/getPage';
export const userOrder_deleteIt = app_server + '/userOrder/deleteIt';
export const userOrder_payIt = app_server + '/userOrder/payIt';


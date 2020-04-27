
import * as Ajax from '@/helper/Ajax';
import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';

export const MAX_ALLOW_BUY = 10;    // 每人最多允许购买数

const CACHE_DATA = 'CACHE_DATA';
const CACHE_VERSION = 'CACHE_VERSION';             // 和服务器比对


/**
 * FIXME  : 未完成 , 2020/4/14
 * 
 * const CACHE_DATA = 'CACHE_DATA';
 * 
 * 缓存数据: 
 * 
 * 
 * 
 * CACHE_VERSION: '12343434334'  // 时间戳
 * CACHE_DATA : // 服务器返回格式
 * 
 * {
 *      'cv': '12343434334', 
 *      'data': 服务器返回数据
 * }
 * 
 * 
 */


// 单个用户允许购买的商品
export function getMaxBuy(){
    return 10;
}

// 需要稳妥
export function usingCache(callback){

    let cv = sessionStorage.getItem(CACHE_VERSION);

    let url = Urls.serverCache_getCache;

    let params = {};
    if(cv){
        params['cv'] = cv;
    }else{
        params['cv'] = 0;
    }

    Ajax.post(url, params, function(serverData){
        let data = serverData;
        if(data.cv == cv){
            // 版本相同, 缓存不需要更新
            let json = sessionStorage.getItem(CACHE_DATA);
            callback(JSON.parse(json));
            return;
        }

        sessionStorage.setItem(CACHE_VERSION, data.cv);
        sessionStorage.setItem(CACHE_DATA, JSON.stringify(data.data));
        callback(data.data);
    });
}


import axios from 'axios';
axios.defaults.withCredentials = true


function _makeSearchParams(params, avoid_names){

    let urlParams = new URLSearchParams(); 
    if(params instanceof Object){
        for(var p in params){
            if(avoid_names && avoid_names.indexOf(p) >= 0){
                // 不添加
                continue;
            }
            urlParams.append(p, params[p]);
        }
    }
    else{
        urlParams = params;
    }
    return urlParams;
}

// ======== 加入方便的根方法. 此方法会让用户传入error 函数进行处理. 
export function post(url,params, succ_func, error_func){

    // 如果需要全局loading 定死loading的名字
    var avoid_params = [
        '_need_loading_'
    ]

    let theParams = null;
    if(params){
        theParams = _makeSearchParams(params, avoid_params);
    }
    let inst = this;

    // console.log('---- check bus');
    // console.log(inst.Bus);


    axios.post(url, theParams).then(function (response) {
            
        let server_data = response.data;
        succ_func(server_data);
    })
    .catch(function (error) {	

        if(error_func){
            error_func(error);
        }else{
            console.log('=================== error ===============');
            console.log(error);
        }
    
    });
} 

export function get(url,params, succ_func, error_func){

    let inst = this;
    let theParams = null;
    if(params){
        theParams = {params};
    }

    axios.get(url, theParams).then(function (response) {

        let server_data = response.data;
        succ_func(server_data);
    })
    .catch(function (error) {	          

        if(error_func){
            error_func(error);
        }else{
            console.log('=================== error ===============');
            console.log(error);
        }
    
    });
}
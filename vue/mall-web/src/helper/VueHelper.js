/**
 * 方便使用vue的类. 硬性规定: 
 * 1. 正在显示的属性必须命名为active
 */

export function getItemById(items, itemId){
    for(let i=0; i < items.length; i++){
        if(items[i].id == itemId){
            return items[i];
        }
    }    
}

export function getUrlParam(queryUrl,name) {

    var arr = queryUrl.split('&');
    for (var i = 0; i < arr.length; i++) {
        var kv = arr[i].split('=');
        if (kv[0] == name) {
            return kv[1];
        }
    }
    return "";
}

export function parseUrlQueryString(){
    var url=location.href;
    var i=url.indexOf('?');
    if(i==-1)return '';
    var querystr=url.substr(i+1);
    return querystr;


    // var v = parseUrl();//解析所有参数
    // console.log(v);
    // alert(v);//就是你要的结果
    // alert(v['brandId']);//就是你要的结果
}

export function parseUrlParams(){
    var querystr = parseUrlQueryString();
    var arr1=querystr.split('&');
    var arr2=new Object();
    for  (i in arr1){
        var ta=arr1[i].split('=');
        arr2[ta[0]]=ta[1];
    }
    return arr2;


    // var v = parseUrl();//解析所有参数
    // console.log(v);
    // alert(v);//就是你要的结果
    // alert(v['brandId']);//就是你要的结果
}

// 检查鼠标是否落在某个物体( div, 其他对象... )
export function isMouseInObject(event, checkDiv){
    
    let X = event.clientX;
    let Y = event.clientY;
    let isIn = false;
    let checkDistance = 5;   // 5px;

    let offsetLeft = checkDiv.offsetLeft;
    let offsetTop = checkDiv.offsetTop;
    let offsetWidth = checkDiv.offsetWidth;
    let offsetHeight = checkDiv.offsetHeight;

    // console.log('==== X: ' + X);
    // console.log('==== Y: ' + Y);

    // 纵向检查
    if( Y < offsetTop || Y > (offsetTop + offsetHeight) ){
        // console.log('==== offsetTop: ' + offsetTop);
        // console.log('==== boxHeight: ' + (offsetTop + offsetHeight));
        isIn = false;
    }
    else{
        // 纵向Y 满足, 检查 X
        // console.log('==== offsetLeft: ' + offsetLeft);
        // console.log('==== boxWidth: ' + (offsetLeft + offsetWidth));

        if( X < offsetLeft ||  X > (offsetLeft + offsetWidth)){
            isIn = false;
        }
        else{
            isIn = true;
        }
    }

    return isIn;
}

// 设置所有属性
export function batchSetProp(items, prop, value){
  
    for(let i=0; i < items.length; i++){
        items[i][prop] = value;
    }      
}


export function resetForm(formObject, excludeFields){

    if(!excludeFields){
        excludeFields = null;
    }else{
        if( typeof(excludeFields) == "string"){
            excludeFields = [excludeFields];
        }
    }

    for(var p in formObject){
        let has = false;
        if(excludeFields){
            excludeFields.forEach(fname => {
                if(p == fname){
                    has = true;
                }
            });
        }
        if(!has){
            formObject[p] = '';
        }
    }
}

export function deleteItemByProp(items, prop, propValue){
  
    let index = -1;
    for(let i=0; i < items.length; i++){
        if(items[i][prop] == propValue){
            index = i;
        }
    }      
    if(index >=0){
        items.splice(index, 1);
    }
}


export function popFullAddress(data){
    if(!data)return '';

    let p = data.provinceName;
    let c = data.cityName;
    let d = data.districtName;
    let info = data.blockInfo;

    if(d){
        return p + '/' + c + '/' + d + ' ' + info;
    }
    return p + '/' + c + ' ' + info;
  }


// 独占式设置属性. 即 A 属性设置之后, 列表的其他对象不能再设置这个值
export function setPropValue(items, prop, propValue){
  
    for(let i=0; i < items.length; i++){
        items[i][prop] = propValue;
    }     
}

// 解决 input autocomplete 无效问题
// 见: https://stackoverflow.com/questions/12374442/chrome-ignores-autocomplete-off
// 经测试不起作用, FIXME 
// export function setAutocompleteOff(theDoc, id){
//     var some_id = theDoc.querySelector(id);
//     console.log('--- some_id: ', some_id);
//     some_id.removeAttribute('autocomplete');
// }


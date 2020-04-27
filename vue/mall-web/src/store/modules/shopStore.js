/**
 * 购物车store
 *  
 */

const max_allow = 100;

const state = {

    // let payload = {
        //     'pid': 
        //     'modalTitle': 
        //     'specValueString': 
        //     'retailPrice': 
        //     'quantity': 1, 
        //     'totalProductFee': 
        // }

    products: [], 
    

}

const mutations = {

    // 从服务器返回数据之后调用这个方法. 
    addProduct(state, payload){
        let prod = null;
        let has = false;
        state.products.forEach(exist => {
            if(exist.pid == payload.pid){
                prod = exist;
                has = true;
            }
        });

        if(has){
            prod.quantity = prod.quantity + payload.quantity;     
        }else{
            prod = payload;
        }

        
        if(prod.quantity >= max_allow){
            prod.quantity = max_allow;
        }   
        prod.totalProductFee = prod.retailPrice * prod.quantity;
        prod.totalProductFee = prod.totalProductFee.toFixed(2);

        if(!has){
            state.products.push(prod);
        }

        // console.log('--- products: ', state.products);
    }, 

    //  删除商品
    deleteProduct(state, idList){
        
        let remainList = [];
        state.products.forEach(prod => {
            if( !idList.includes(prod.pid)){
                remainList.push(prod);
            }
        });

        state.products = remainList;

        // console.log('--- products: ', state.products);
    }, 

    // 批量更新
    updateProducts(state, productList){

        state.products = productList;
    },

    clearProducts(state){
        state.products = [];
    }, 

        
}


const getters = {
    productCount(state){
        return state.products.length;
    }, 

    // 获取商品的id
    productIds(state){
        let pids = [];
        state.products.forEach(element => {
            pids.push(element.pid);
        });

        return pids;
    }, 

    productList(state){
        return state.products;
    }, 

    // 所有费用
    totalProductFee(state){
        let total = 0;
        state.products.forEach(prod => {
            total += Number(prod.totalProductFee);
        });
        return total.toFixed(2);
    }, 

}

export default {
    namespaced: true,
    state,
    getters, 
    mutations,
    // actions
}
/**
 * 
 * ########################### 注意!!!!
 * 
 * 这里保存的信息并不保证用户还保持登录状态. 
 * 只是为了头部信息好看. 
 * 
 */


const state = {
    
    isLogin : false, 
    account: '', 
    leftMenu: '',           // 个人中心左侧菜单

}

const mutations = {

    // 从服务器返回数据之后调用这个方法. 
    initUser(state, payload){
        state.isLogin = true;
        state.account = payload.account;
    }, 

    clearUser(state){
        state.isLogin = false;
        state.account = null;
    },

    setLeftMenu(state, menu){
        state.leftMenu = menu;
    },
        
}


const getters = {

    isLogin: (state) => {
        return state.isLogin;
    },
    userInfo: (state) => {
        return {
            'account': state.account, 
        }
    },

    leftMenu: (state) => {
        return state.leftMenu;
    },
}

export default {
    namespaced: true,
    state,
    getters, 
    mutations,
    // actions
}
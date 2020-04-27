<template>
    <div class="actual-box">
        <div class='each-box' v-for="data in dataList" 
            :key="'link-' + data.id" @click="_goUrl(data)">
                {{data.name}}
        </div>  
        <div style="clear:both"></div>
        <ModalLogin ref="ModalLogin"/>
    </div>
</template>

<script>

// =============== 底部友情链接分类
/**
 * ############ 非常重要的 !!!!!!!!!!!!!!!!! 
 * 
 * 因为此控件每个页面都需要, 所以用于处理各种控件的Bus 事件
 * 
 * 
 */

    import * as VueHelper from '@/helper/VueHelper';
    import * as Urls from '@/helper/Urls';
    import * as Ajax from '@/helper/Ajax';
    import * as Events from '@/helper/Events';
    import { mapState, mapMutations, mapGetters } from 'vuex'
    import store from '@/store'
    import ModalLogin from '@/components/biz/ModalLogin';


export default {

    store, 

  data () {
    return {
        dataList: [

        ], 
    }
  }, 

    computed: {

    }, 
    components: {
             ModalLogin
    }, 

    methods: {

        _goUrl(data){
            if(data.link){
                window.open(data.link);
            }
            else{
                // FIXME 转到帮助文章
            }
        }, 

        // 检查服务器的session
        _checkLogin(callback){

            let inst = this;

            let url = Urls.user_checkLogin;
            let params = {}
            
            Ajax.post(url, params, function(serverData){
                let isLogin = serverData.data;
                if(isLogin){
                    if(callback)callback();
                    return;
                }

                inst.$refs.ModalLogin.setCallback(callback);
                inst.$refs.ModalLogin.show();
            });
        },


        _loadData (){
            
            let inst = this;

            let url = Urls.homeIndex_getBottomLinks;
            let params = {
            }
            
            Ajax.post(url, params, function(serverData){
                inst.dataList = serverData.data;
            });
        }, 

    }, 

    mounted(){
        this._loadData();

        // 仅登录事件
        let event = Events.NEED_MODAL_LOGIN;
        this.$root.Bus.$off(event).$on(event, this._checkLogin);
    }, 
}
</script>

<style lang="scss" scoped>

    .actual-box {
        width: 1000px;
        margin: 0 auto; 
        position: relative;
        text-align: center;

        .each-box{
            float: left;
            font-size: 12px;
            color: #666;        
            padding: 0 5px;
            margin-bottom: 10px;
            cursor: pointer;
            &:hover{
                color: firebrick;
            }
            

        }
    }
    
    
</style>

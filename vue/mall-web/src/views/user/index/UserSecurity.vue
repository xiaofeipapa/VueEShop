<template>
<div id="app">
        <!-- ========= 右侧内容区 begin ===================== -->
        <div class='g-content-right'>
            
            <div class="row">
                <span class="label">请输入原密码: </span>
                <input type="password" v-model="opass">
            </div>
            
            <div class="row">
                <span class="label">请输入新密码: </span>
                <input type="password" v-model="newPass">
            </div>
            
            <div class="row">
                <span class="label">再次确认密码: </span>
                <input type="password" v-model="rePass">
            </div>
            
            <div class="row" style="margin-top:20px;">
                <span class="btn" @click="_doSave">提交</span>
                <span class="error" v-if="errorMsg">{{errorMsg}}</span>
            </div>
            
        </div>
        <!-- ========= 右侧内容区 end ===================== -->

        <ModalMsg ref="ModalMsg" />
</div>
</template>

<script>

import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';
import * as Ajax from '@/helper/Ajax';
import ModalMsg from '@/components/com/ModalMsg';

export default {
name: 'security',
  data() {
    return {
        opass: '',          // 原密码
        newPass: '',       // 新密码
        rePass: '',        // 确认密码      
        errorMsg: '', 
    }
  },
  
  components: {
      ModalMsg, 
  },
  
  //已成功挂载，相当ready()
  mounted(){
  },
  //相关操作事件
  methods: {

      _doSave(){

        let inst = this;
        let url = Urls.user_changePass;
        let params = {
            'opass': this.opass, 
            'newPass': this.newPass, 
            'rePass': this.rePass, 
        }

        Ajax.post(url, params, function(serverData){

            // 登录超时
            if(serverData.error && serverData.errorCode == 'CODE_SESSION_EXPIRE'){
                inst.$root.Bus.$emit(Events.NEED_MODAL_LOGIN);
                return;
            }

            // 正常错误
            if(serverData.error){
                inst.errorMsg = serverData.error;
                return;
            }

            inst.errorMsg = '';
            inst.$refs.ModalMsg.show('密码已修改');
        });
      }, 
     
  },
  computed:{
      
  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    .g-content-right{
        float: left;
        width: 958px;
        margin-left: 40px;
        
        .row{
            line-height: 50px;

            input{
                margin-left: 40px;

                @include g-input;
            }

            .label{
                color: #666;
            }
            .error{
                color: $g-red;
                margin-left: 20px;
            }

            .btn {
                @include g-button;
                margin-left: 150px;
            }
        }
    }

</style>

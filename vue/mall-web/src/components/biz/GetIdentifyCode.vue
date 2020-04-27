<template>
<span class="identify-code-wrap">
    <a @click="getIdentifyCode">
        <slot name="button" :invalid="isInvalid" :identifyCodeTxt="identifyCodeTxt"></slot>
    </a>
    <slot name="msg" :getIdentifyCodeMsg="getIdentifyCodeMsg"></slot>

</span>
</template>

<script>

import * as Urls from '@/helper/Urls';
import * as Ajax from '@/helper/Ajax';

//获取验证码
export default {
  name: 'GetIdentifyCode',
  props:{
      phone:{
          type:String,
          default:'',
      },
      //获取验证码的接口地址
      url:{
          type:String,
          default:'',
      },
  },
  watch:{
      phone(newValue,oldValue){
          this.phone = newValue;
      },
      url(newValue,oldValue){
          this.url = newValue;
          this.init();
      },
  },
  data() {
    return {
        identifyCodeTxt:"获取验证码",       //获取验证码按钮文字
        getIdentifyCodeMsg:"",              //获取验证码接口提示信息
        isInvalid:false,                 //按钮失效

        timer:null,         // 定时器

    }
  },
  
  //相关操作事件
  methods: {
    init(){
        this.identifyCodeTxt="获取验证码";
        this.getIdentifyCodeMsg="";
        this.isInvalid=false;
        clearInterval(this.timer);
    },
    getIdentifyCode(){
        let inst = this;
        // console.log('--- getIdentifyCode');
        inst.getIdentifyCodeMsg = '';
        if(inst.identifyCodeTxt == "获取验证码" || inst.identifyCodeTxt == "重新获取"){
          
            var phone = inst.phone;
            if(phone == ""){
                inst.getIdentifyCodeMsg = "请输入手机号码";
            }else if(!/^1[0-9]{10}$/.test(phone)){
                inst.getIdentifyCodeMsg = "请输入合法的手机号码";
            }else{
                inst.isInvalid = true;
                inst.identifyCodeTxt = "获取中..."
                let params = {
                    phone: phone,          
                };

                let url = this.url;

                Ajax.post(url, params, function(server_data){
                    if(server_data.error){

                        inst.isInvalid = false;
                        inst.identifyCodeTxt = "获取验证码";
                        inst.getIdentifyCodeMsg = server_data.returnMsg || server_data.error;
                        return;
                    }
                    var count = 60;
                    inst.timer = setInterval(function(){
                        if(count == 0){
                            clearInterval(inst.timer);
                            inst.isInvalid = false;
                            inst.identifyCodeTxt = "重新获取";
                        }else{
                            inst.isInvalid = true;
                            inst.identifyCodeTxt = "已发送" + count + "s";
                            count--;
                        }
                    }, 1000);
                    
                    inst.getIdentifyCodeMsg = "";
                });
                            

            }
        }
    },
    clearIdentify(){
        let inst = this;
        clearInterval(inst.timer);
        inst.isInvalid = false;
        inst.identifyCodeTxt = "获取验证码";
    }
  },
  watch:{
      getIdentifyCodeMsg(newValue){
          if(newValue){
              this.$emit("getIdentifyCodeMsg",newValue)
          }
      }
  }
}
</script>

<style lang="scss" scoped type="text/css">


</style>

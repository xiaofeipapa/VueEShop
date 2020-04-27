<template>
<DModal
    :width='600'
    :height='520'
    @onHide="_clearInfo"
    ref='modalBox'
        >
        <div slot='header'>
            <div class="layout-title">{{layoutTitle}}收{{txt}}地址<span class="close" @click="hide()">×</span></div>
        </div>
        <div slot="body">   
                
            <div class="layout-content">
                <div>
                    <label>
                        <span class="sign">*</span>收{{txt}}人姓名：</label>
                        <br>
                        <input type="text" class="name-inp" v-model="dataForm.name"></div>
                <div><label><span class="sign">*</span>收{{txt}}地址：</label><br>
                    <AddressSelector 
                        ref="AddressSelector"
                        
                        /><br>
                    <input type="text" class="detail-address" placeholder="详细地址" v-model="dataForm.blockInfo">
                </div>

                <div>
                    <span class="span"><label><span class="sign">*</span>手机号码(或固定电话号码)：</label>
                    <br><input type="text" class="cellphone-inp" v-model="dataForm.phone"></span>

                    <span class="span" style="margin-left:25px;"><label>邮政编码：</label>
                    <br><input type="text" class="cellphone-inp" v-model="dataForm.post"></span>
                    
                </div>
                <div>
                    <span class="span"><label>邮箱：</label>
                    <br><input type="text" class="cellphone-inp" v-model="dataForm.email"></span>
                    
                </div>
                
                <div class="save-address" @click="_saveData()">
                    保存收{{txt}}地址                        
                </div>
                <div style="margin-top:10px; text-align:center;">                    
                    <span class="save-tips" v-if="errorMsg">{{errorMsg}}</span>
                </div>
            </div>
        </div>
        <div class='clear-both'></div>
    </DModal>
</template>

<script>
/** 
 * ========= 增加地址信息的模态框 ============= 
 * 
 *  invoiceAddress : 发票地址
 *  goodAddress : 收货地址
 * 
 */

import * as Ajax from '@/helper/Ajax';
import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';
import * as Events from '@/helper/Events';
import AddressSelector from 'components/com/AddressSelector';
import DModal from 'components/com/DModal';

export default {
    components: {
        DModal, AddressSelector
    },
    props: ['addressCat'],
    data() {
        return {     
            
            dataForm: {
                'id': '', 
                'userId': '',       // 属于哪个用户
                'name': '', 
                'phone': '', 
                'email': '', 

                'provinceCode': '', 
                'provinceName': '', 
                'cityCode': '', 
                'cityName': '', 
                'districtCode': '', 
                'districtName': '', 

                'blockInfo': '', 
                'post': '', 
                'defaultFlag': 'N', 
            }, 

            errorMsg: '', 
            layoutTitle:"添加",   //头部标题文字
        }
    },
    mounted(){
        
    },
    watch:{
	
    },
    computed:{
        txt(){
            if(this.addressCat == 'goodAddress'){
                return '货'
            }
            if(this.addressCat == 'invoiceAddress'){
                return '票'
            }
        }
    },
    //相关操作事件
    methods: {
    
        _saveData(){
            
            let inst = this;

            let url = Urls.userAddress_saveDa;

            this.dataForm.addressCat = this.addressCat;
            let address = this.$refs.AddressSelector.getData();
            this.dataForm = Object.assign(this.dataForm, address);

            let params = this.dataForm;

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

                // 正常处理
                inst.errorMsg = '';
                inst.dataForm.id = serverData.data;
                let payload = Object.assign(inst.dataForm, {});
                inst.$emit('saveSucc', payload);
                inst.$refs.modalBox.hide();
            });
        },
         
        _clearInfo(){
            VueHelper.resetForm(this.dataForm);
            this.errorMsg=""
            this.layoutTitle="添加";
        },

        setDataVo(data){
            let d = Object.assign({}, data);
            this.dataForm = d;
            this.show();

            let inst = this;
            inst.$nextTick(_ => {
                inst.$refs.AddressSelector.setDataVo(d);
            });
        }, 
        
        show() {

            this.$refs.modalBox.show();
            let inst = this;
            inst.$nextTick(_ => {
                inst.$refs.AddressSelector.init();
            });

        }, 
        hide() {
            this.$refs.modalBox.hide();
        }, 
    }
}
</script>
<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';

    $border1:1px solid #dad8d8;
    $color-grey:#999;
    
    .layout-title{
        position: relative;
        padding: 0 20px;
        line-height: 38px;
        // background-color: #f8f8f8;
        border-bottom: solid 1px #ddd;
        .close{
            position: absolute;
            right: 20px;
            text-align: center;
            font-size: 30px;
            line-height: 36px;
            top: 0;
            cursor: pointer;
        }
    }

    .layout-content{
        padding: 0 20px;  
        font-size: 13px;
        > div{
            margin-bottom:20px;
        }
        label{
            line-height:30px;
        }
        .sign{
            color:$g-main-color;
            margin-right: 3px;
        }
        input{
            border: $border1;
            height:28px;
            line-height:28px;
                padding: 0 3px;
                font-size:13px;
        }
        .span{
            display:inline-block;
        }
        .name-inp{
            width: 244px;
            
        }
        .detail-address{
            margin-top: 5px;
            width: 430px;
        }
        .cellphone-inp{
            width: 196px;
        }
        .phone-inp{
            width: 196px;
        }
        .post-inp{
            width: 100px;
        }
        .margn-left-right{
            margin-left:7px;
            margin-right:7px;
        }
        .save-address{
            width: 200px;
            height: 50px;
            font-size: 16px;
            text-align: center;
            line-height: 50px;
            margin: auto;
            background-color:$g-main-color;
            color: #fff;
            cursor: pointer;
            margin-top: 20px;
            position: relative;
        }
        .save-tips{
            color:$g-main-color;
            text-align: center;
            font-size: 15px;
        }

        .stock-address{
            border: $border1 !important;
            .head{
                height: 28px !important;
                line-height: 28px !important;
            }
            .tab{
                display: none !important;
            }
        }
    }

    .d-select{
        border: 1px solid gray;
        padding: 6px 15px;
        cursor: pointer;
        

        &.check{
            border: 1px solid #ff0000;
        }
        &.right{
            margin-left: 10px;
        }
    }




</style>

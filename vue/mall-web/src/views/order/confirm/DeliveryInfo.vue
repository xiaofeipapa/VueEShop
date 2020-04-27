<template>
        
    <!-- ================ 收货人信息 begin -->
    <div class='c-address-box'>
        <div class="c-title">
            <div class="left">
                收货人信息
            </div>    
            <div class="right">
                <a @click="_openAdd()">新增收货地址</a>
            </div>    
        </div> 
        <div class='row-2'>
            <template v-if="selectDa">
                <div>
                    <span class="c0" :class="{'checked': true}" >{{selectDa.name}}</span>
                    <span class="c1"><span>{{selectDa.name}}</span></span>
                    <span class="c2"><span>{{selectDa.phone}}</span></span>
                    <span class="c3 address-text">{{_popFullAddress(selectDa)}}</span>                    
                    <span class="c2"><span v-if="selectDa.defaultFlag == 'Y'">默认</span></span>
                    <span class="c4" @click="_showDelete(selectDa)">删除</span>
                    <span class="c4" @click="_openEdit(selectDa)">修改</span>
                    <span class="c4" 
                        v-if="selectDa.defaultFlag == 'N'"
                        @click="_setDefault(selectDa.id)">设为默认</span>
                </div>
                <div class="other-ad" v-if="otherList.length > 0 && !isShowAddressList" @click="isShowAddressList = true">选择其他地址 <i class="fa fa-angle-down"></i></div>
                <div class="other-ad" v-if="otherList.length > 0 && isShowAddressList" @click="isShowAddressList = false">收起 <i class="fa fa-angle-up"></i></div>
                <ul class="list" v-show="isShowAddressList">
                    <li v-for="ad in otherList" :key="ad.id">
                        <div>
                            <span class="c0" @click="_changeAddress(ad)">{{ad.name}}</span>
                            <span class="c1"><span>{{ad.name}}</span></span>
                            <span class="c2"> <span>{{ad.phone}}</span></span>
                            <span class="c3 address-text">{{_popFullAddress(ad)}}</span>       
                            <span class="c2"><span v-if="ad.defaultFlag == 'Y'">默认</span></span>
                            <span class="c4" @click="_showDelete(ad)">删除</span>
                            <span class="c4" @click="_openEdit(ad)">修改</span>
                            <span class="c4" 
                                v-if="ad.defaultFlag == 'N'"
                                @click="_setDefault(ad.id)">设为默认</span>
                        </div>
                    </li>
                </ul>
                
            </template>
            <template v-else>
                您还没添加收货地址. 请
                <span class="add-address-btn" @click="_openAdd()">点此添加</span>
            </template>
        </div>    

        <ModalAddOrEditAddress ref="ModalAddOrEditAddress" 
                :addressCat="'goodAddress'"
                @saveSucc="_loadAddressList"/>

        <ModalConfirm ref="ModalConfirm"
            title="确认删除这个地址?"
            okText="确认"
            cancelText="取消"
            @ok="_deleteAddress"
            />

    </div>
    <!-- ================ 收货人信息 end -->

</template>

<script>

    import * as Ajax from '@/helper/Ajax';
    import * as VueHelper from '@/helper/VueHelper';
    import * as Urls from '@/helper/Urls';
    import * as Events from '@/helper/Events';
    import store from '@/store'
    
    import TopUserSpan from '@/components/biz/TopUserSpan';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';
    import LoadingScreen from 'components/com/LoadingScreen';
    import ModalAddOrEditAddress from 'components/biz/ModalAddOrEditAddress';
    import ModalConfirm from 'components/com/ModalConfirm';
    

export default {
  data() {
    return {
        isShowAddressList: false,       // 显示除默认外的地址列表
        otherList: '', 
        selectDa: null,                 // 当前选择的地址

        deleteId: '',                   // 需要删除的地址

    }
  },
  store,
  components: {
      TopUserSpan, BottomArticle, BottomLink, LoadingScreen, 
      ModalAddOrEditAddress,ModalConfirm

  },

  computed: {
      
  }, 
  
  watch: {

  }, 
  //相关操作事件
  methods: {

        // 外部方法调用
        doInit(){
            this._loadAddressList();
        }, 

        // 外部方法调用
        getData(){
            let data = Object.assign({}, this.selectDa);
            return data;
        },

        _popFullAddress(data){
            return VueHelper.popFullAddress(data);
        },

        _changeAddress(data){
            // 将当前选择推到列表, 将此data变成当前选择
            let newd = Object.assign({}, data);
            // console.log('-- newd: ', newd);

            // 删除这个选择
            VueHelper.deleteItemByProp(this.otherList, 'id', data.id);

            // 推入原来的选择
            let exist = Object.assign({}, this.selectDa);
            this.otherList.push(exist);

            // 切换
            this.selectDa = newd;

        },  


        _showDelete(item){
            this.deleteId = item.id;
            this.$refs.ModalConfirm.show();
        },
        _deleteAddress(){

            let inst = this;

            let url = Urls.userAddress_deleteIt;
            let params = {
                'id': this.deleteId, 
            }

            Ajax.post(url, params, function(serverData){

                // 登录超时
                if(serverData.error && serverData.errorCode == 'CODE_SESSION_EXPIRE'){
                    inst.$root.Bus.$emit(Events.NEED_MODAL_LOGIN);
                    return;
                }

                // 正常错误
                // if(serverData.error){
                //     inst.errorMsg = serverData.error;
                //     return;
                // }

                // 正常处理
                inst._handleAddress(serverData);
            });
        }, 
              
        // 增加新地址
        _openAdd(){
            this.$refs.ModalAddOrEditAddress.show();
        }, 
              
        // 编辑
        _openEdit(item){
            this.$refs.ModalAddOrEditAddress.setDataVo(item);
            this.$refs.ModalAddOrEditAddress.show();
        }, 

        _setDefault(id){

            let inst = this;

            let url = Urls.userAddress_setDefault;
            let params = {
                'id': id, 
                'cat': 'goodAddress', 
            }

            Ajax.post(url, params, function(serverData){

                // 登录超时
                if(serverData.error && serverData.errorCode == 'CODE_SESSION_EXPIRE'){
                    inst.$root.Bus.$emit(Events.NEED_MODAL_LOGIN);
                    return;
                }

                // 正常错误
                // if(serverData.error){
                //     inst.errorMsg = serverData.error;
                //     return;
                // }

                // 正常处理
                inst.otherList.forEach(element => {
                    if(element.id == id){
                        element.defaultFlag = 'Y';
                    }
                    else{
                        element.defaultFlag = 'N';
                    }
                });
                
                if(inst.selectDa.id == id){
                    inst.selectDa.defaultFlag = 'Y';
                }
                else{
                    inst.selectDa.defaultFlag = 'N';
                }
            });
        }, 

        // 保存新增或编辑的收货地址
        _loadAddressList(){
                        
            let inst = this;

            let url = Urls.userAddress_getList;
            let params = {
                'cat': 'goodAddress', 
            }

            Ajax.post(url, params, function(serverData){

                // 登录超时
                if(serverData.error && serverData.errorCode == 'CODE_SESSION_EXPIRE'){
                    inst.$root.Bus.$emit(Events.NEED_MODAL_LOGIN);
                    return;
                }

                // 正常错误
                // if(serverData.error){
                //     inst.errorMsg = serverData.error;
                //     return;
                // }

                // 正常处理
                inst._handleAddress(serverData);
            });
        },
        _handleAddress(serverData){

            let inst = this;

            if(!serverData.data || serverData.data.length < 1){
                inst.otherList = [];
                inst.selectDa = null;
                return;
            }


            // 正常处理
            let otherList = [];
            serverData.data.forEach(element => {
                if(element.defaultFlag == 'Y'){
                    inst.selectDa = element;
                }
                else{
                    otherList.push(element);
                }
            });
            inst.otherList = otherList;

            // 发送事件
            if(inst.selectDa){
                inst.$emit('selectAddress', inst.getData());
            }
        }, 
  },
  mounted(){
      this._loadAddressList();
    
  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    $border1:solid 1px #dddddd;

    /** 地址 **/
    .c-address-box{
        padding: 0 36px;
        border: solid 1px #eee;
        margin-top: 20px;
        .c-title{
            height: 50px;
            line-height: 50px;
            border-bottom: $border1;
            .left{
                float: left;
                font-size: 16px;
                font-weight: bold;
            }
            .right{
                float: right;
                padding-right: 20px;
                a{
                    cursor: pointer;
                    font-size: 14px;
                }
            }
        }
        .check{
            line-height: 40px;
            background-color: #eee;
            margin-bottom: 20px;
            font-size: 15px;
            width: 1200px;
            position: relative;
            left: -36px;
        }
    }

    .add-address-btn{
        color: $g-main-color;
        cursor: pointer;
    }


.row-2{
        clear: both;
        line-height: 50px;
        font-size: 14px;
        margin-bottom: 20px;
        .c0{
            margin-right: 30px;
            border: solid 1px #999;
            cursor: pointer;
            width: 100px;
            display: inline-block;
            text-align: center;
            line-height: 26px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            vertical-align: middle;
            padding: 0 10px;
            &:hover{
                color: $g-main-color;
            }
            &.checked{
                border: solid 1px $g-main-color;
                background: url(../../../assets/image/prod_selected.png) no-repeat right bottom;
            }
            
        }
        .c1{
            margin-right: 40px;
            vertical-align: middle;
        }
        .c2{
            margin-right: 40px;
            vertical-align: middle;
        }

        .c3{
            overflow: hidden;
            text-overflow: ellipsis;
            display: inline-block;
            white-space: nowrap;
            vertical-align: middle;
            width: 426px;
        }
        .c4{
            float: right;
            margin-right: 10px;
            cursor: pointer;
            color: #999;
            font-size: 12px;
            &:hover{
                color: $g-main-color;
                    
            }
        }
        .address-btn{
            padding: 0 5px;
            color: #fff;
            background: #999;
            margin-top: 14px;
            font-size: 12px;
            float: right;
            height: 20px;
            line-height: 20px;
        }
        .ad-span{
            .as-left{
                float: left;
            }
            .as-right{
                // margin-left: 200px;
                border-top: solid 1px #ddd;
                clear: both;
                padding: 0 20px;
            }
            .add{
                color: #999;
                cursor: pointer;
            }
            .radio-s{
                margin-right: 50px;
                input{
                    display: none;
                }
                label{
                    display: inline-block;
                    width: 14px;
                    height: 14px;
                    position: relative;
                    background: #fff;
                    border: 1px solid #ccc;
                    margin-right: 5px;
                    border-radius: 10px;
                    vertical-align: middle;
                    cursor: pointer;
                }
                input:checked + label:after {
                    content: '';
                    position: absolute;
                    width: 10px;
                    margin: 2px 1px 2px 2px;
                    height: 4px;
                    background: transparent;
                    border: 2px solid #c7000b;
                    border-top: none;
                    border-right: none;
                    transform: rotate(-45deg);
                }
            }
        }
        
    }
    .other-ad{
        cursor: pointer;
        color: #999;
        font-size: 12px;
        line-height: 20px;
    }
    .list{
        width: 100%;
        font-size: 14px;
        max-height: 93px;
        overflow-y: scroll;
        li{
            float: none;
            line-height: 30px;
            cursor: pointer;
        }
    }
</style>


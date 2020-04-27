<template>

      <div class='g-content-box'>
          
        <div class='g-content-right'>
          
            <div class="my-order-wrap">
                
                <div class='top-title fs-14'>
                    <span class="fl">我的订单</span>
                </div>
                <div class="top-operate">
                    <span class="tag-item" v-bind:class="showOrderCat == 1 ? 'current': ''"
                        @click="_changeCatAndSearch(1)"
                        >全部订单</span>
                    <span  class="tag-item"
                        @click="_changeCatAndSearch(2)" v-bind:class="showOrderCat == 2 ? 'current': ''"
                        >待付款
                        <span v-if="waitPaidNum" class="noty-num">{{waitPaidNum}}</span>
                    </span>
                    <span  class="tag-item" v-bind:class="showOrderCat == 3 ? 'current': ''"
                        @click="_changeCatAndSearch(3)"
                        >待收货
                        <span v-if="waitAcceptNum" class="noty-num">{{waitAcceptNum}}</span>
                    </span>

                    <span class="search-bar">
                        <input type="text"
                            id='sInput'
                            ref="searchInput"
                        placeholder="商品名称 / 商品编号 / 订单号" v-model="searchText">
                    <span class="search-pg" @click="getOrder(1)"></span></span>
                </div>

                <template v-if="orderList.length == 0">
                    <div class="is-loading" v-if="isLoading"><img src='../../../assets/image/loading.gif' /> 正在加载,请稍候......</div>
                    <div class="no-data" v-else>暂无数据</div>
                </template>
                <template v-else>

                <table class="order-table">
                    <thead class="t-h">
                        <th class="h1">订单详情</th>
                        <th class="h2">收货人</th>
                        <th class="h3">金额</th>
                        <th class="h4">全部状态</th>
                        <th class="h5">操作</th>
                    </thead>

                    <tbody v-for="item in orderList" :key="item.id" class="t-body">
                        <tr class="t-info">
                            <td colspan="5">
                                <span class="order-time">{{item.createTimeStr}}</span>
                                <span class="order-no">订单号：<span>{{item.bizId}}</span></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="h1">
                                <div class="item-info" v-for="orderItem in item.itemList" :key="orderItem.id">
                                    <div class="i-left">
                                        <a :href="'/views/product/detail.html?pid='+orderItem.productId">
                                            <img :src="orderItem.imagePath+'?x-oss-process=image/resize,m_fill,w_80,h_80'">
                                        </a>
                                    </div>
                                    <div class="i-right">
                                        <div class="m-title">
                                            {{orderItem.modalTitle}} <p/>
                                            <span class="other">{{orderItem.specValueString}}</span>
                                            <span class="other ml">× {{orderItem.quantity}}</span>
                                        </div>
                                    </div>
                                    <div style="clear:both"></div>
                                </div>
                            </td>
                            <td class="h2">
                                <div class="user-info">
                                    {{item.deliveryUser}}
                                </div>
                            </td>
                            <td class="h3">
                                <div class="user-info">
                                    ￥{{Number(item.totalShouldPaid).toFixed(2)}}
                                </div>
                            </td>
                            <td class="h4">
                                <div v-if="item.dataStatus == 'PAY_EXPIRE'" class="color-grey">已超时</div>    
                                <div v-if="item.dataStatus == 'PAID'">待签收</div>                                
                            </td>
                            <td class="h5">
                                <div class="btn-group">
                                    <div class="o-btn"
                                        v-if="item.dataStatus == 'PAY_EXPIRE'"
                                         @click="_showDeleteModel(item.bizId)">删除</div>

                                    <div class="o-btn"
                                        v-if="item.dataStatus == 'PAID'"
                                         @click="_showDeleteModel(item.bizId)">签收</div>
                                </div>
                            </td>
                        </tr>   
                    </tbody>
                </table>
                
                    <!-- 分页条沿用已有控件 -->
                    <Pagination :pageCount="pageCount" 
                        :pageNo="pageNo" @pageNo="_changePageAndGo">
                    </Pagination>

                </template>
                
            </div>

        </div>

        <ModalConfirm ref="deleteModel" title="删除" okText="确认" cancelTxt="取消"
            :width="360" :height="166"
            @ok="_usingIdToServer"
        >
            <div slot="content">
                <div class="dm-txt">
                    <p class="p1">删除这条订单?此操作不可恢复!</p>
                </div>
            </div>
        </ModalConfirm>

      </div>
    

</template>

<script>

import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';
import * as Events from '@/helper/Events';
import * as Ajax from '@/helper/Ajax';

import ModalConfirm from 'components/com/ModalConfirm';
import Pagination from 'components/com/Pagination';

//显示的订单类型, 1表示所有, 2表示待付款 3 表示待收货
const SHOW_ORDER_CAT_ALL = 1;
const SHOW_ORDER_CAT_TO_PAY = 2;
const SHOW_ORDER_CAT_TO_GET = 3;

export default {
    name: 'myOrder',
    data() {
        return {
            pageCount:0,            //结果共多少页
            pageNo:1,               //当前页数
            pageSize: 10, 
            showOrderCat:  SHOW_ORDER_CAT_ALL,         
      
            orderList:[],

            bizId:"",          //正在操作的订单号
            ordering:"",          //正在操作的订单
            msg:"",                 //提示信息
            _buyAgainCode:"",

            waitPaidNum: 0,            //待付款数量
            waitAcceptNum: 0,         //待收货数量
            waitCommentNum: 0,         //待评价数量

            searchText: '',             // 查询关键字
            isLoading: false, 
        }
    },

  components: {
      Pagination, ModalConfirm
  },
  
  computed: {


      
  }, 
  watch:{
      
  },
  //相关操作事件
  methods: {

      _changeCatAndSearch(cat){
          this.showOrderCat = cat;
          this._getOrderList();
      }, 
    
    _changePageAndGo(value){
        this.pageNo = value;
        this._getOrderList();
    },
    _getOrderById(id){
        let inst = this;
        // 加载数据
        // Lib.M.ajax({
        //     'url': Urls.get_order_byid,
        //     'data':{
        //         bizId:id
        //     },
        //     'success':function (server_data){
        //         if(server_data.sessionExpire == 'Y'){
        //             window.location.href="/views/user/login.html";
        //             return;
        //         }
        //         var data = server_data.data;
        //         if(data){
        //             for(let i = 0 ; i < inst.orderList.length ; i++){
        //                 if(inst.orderList[i].bizId == id){
        //                     // inst.orderList[i] = data;
        //                     inst.$set(inst.orderList,i,data);
        //                 }
        //             }
        //         }

        //     }
        // });
    },
    showCancelModel(bizId){
         this.$refs.cancelModel.show();
         this.bizId=bizId
    },
    cancelOrder(){
        let inst = this;
        // 加载数据
        // Lib.M.ajax({
        //     'url': Urls.order_cancel,
        //     'data':{
        //         bizId:inst.bizId,
        //     },
        //     'success':function (server_data){
        //         if(server_data.sessionExpire == 'Y'){
        //             window.location.href="/views/user/login.html";
        //             return;
        //         }
        //         if(server_data.returnCode == '0'){
        //             inst._getOrderById(inst.bizId);//更新状态
        //             inst.msg = "已取消";
        //             inst.$refs.msgModel.show();
        //         }else{
                    
        //             inst.msg = server_data.error;
        //             inst.$refs.msgModel.show();
        //         }
        //     }
        // });
    },
    showFinishModel(item){
         this.$refs.finishModel.show();
         this.ordering=item
    },
    finishOrder(){
        let inst = this;
        // 加载数据
        // Lib.M.ajax({
        //     'url': Urls.order_finish,
        //     'data':{
        //         bizId:inst.ordering.bizId,
        //     },
        //     'success':function (server_data){
        //         if(server_data.sessionExpire == 'Y'){
        //             window.location.href="/views/user/login.html";
        //             return;
        //         }
        //         if(server_data.returnCode == '0'){
        //             inst._getOrderById(inst.ordering.bizId);//更新状态
        //             inst.msg = "已确认收货";
        //             inst.$refs.msgModel.show();
        //         }else{
                    
        //             inst.msg = server_data.error;
        //             inst.$refs.msgModel.show();
        //         }
        //     }
        // });
    },
    _showDeleteModel(bizId){
         this.bizId = bizId
         this.$refs.deleteModel.show();
    },

    // 删除 关注 再次购买 的共同逻辑
    _usingIdToServer(){
        let inst = this;
        let url = Urls.userOrder_deleteIt;
        let params = {
            'oid': this.bizId, 
        }

        Ajax.post(url, params, function(serverData){

            // 登录超时
            if(serverData.error && serverData.errorCode == 'CODE_SESSION_EXPIRE'){
                inst.$root.Bus.$emit(Events.NEED_MODAL_LOGIN);
                return;
            }

            // 错误
            if(serverData.error){
                window.location.href = '/views/common/e500.html';
                return;
            }

            // 正常处理
            inst._getOrderList();
        });

    }, 

    deleteOrder(){
        let inst = this;
        // 加载数据
        // Lib.M.ajax({
        //     'url': Urls.order_delete,
        //     'data':{
        //         bizId:inst.bizId,
        //     },
        //     'success':function (server_data){
        //         if(server_data.sessionExpire == 'Y'){
        //             window.location.href="/views/user/login.html";
        //             return;
        //         }
        //         if(server_data.returnCode == '0'){
        //             for(let i = 0 ; i < inst.orderList.length ; i++){
        //                 if(inst.orderList[i].bizId == inst.bizId){
        //                     inst.orderList.splice(i,1);
        //                 }
        //             }
        //             inst.msg = "已删除";
        //             inst.$refs.msgModel.show();
        //         }else{
                    
        //             inst.msg = server_data.error;
        //             inst.$refs.msgModel.show();
        //         }
        //     }
        // });
    },
    _buyAgain(bizId){
        let inst = this;
        // 加载数据
        // Lib.M.ajax({
        //     'url': Urls.order_buy_again,
        //     'data':{
        //         bizId:bizId,
        //     },
        //     'success':function (server_data){
        //         if(server_data.sessionExpire == 'Y'){
        //             window.location.href="/views/user/login.html";
        //             return;
        //         }
        //         if(server_data.data){
        //             inst._buyAgainCode = server_data.data.code;
        //             inst.$refs._buyAgainModel.show();
        //         }
        //     }
        // });
        

    },
    _getOrderList(){
        
        let inst = this;
        let url = Urls.userOrder_getPage;
        let params = {
            'cat': this.showOrderCat, 
            'pageNo': this.pageNo, 
            'pageSize': this.pageSize, 
            'q': this.searchText, 
        }

        this.isLoading = true;

        Ajax.post(url, params, function(serverData){

            inst.isLoading  = false;

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
            let page = serverData.data;

            inst.orderList = page.dataList;
            inst.pageCount = page.pageCount; 
            inst.waitPaidNum = page.waitPaidNum; 
            inst.waitAcceptNum = page.waitAcceptNum; 
        });
        
    },
  },
  //已成功挂载，相当ready()
  mounted(){

    let event = Events.CHILD_INIT;
    this.$root.Bus.$off(event).$on(event, this._getOrderList);
	
  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';


$border1:1px solid #dad8d8;
$background-color:#f8f8f8;
$color-grey:#999;
@mixin pading-l-r{
    padding-left: 20px;
    padding-right: 20px;
}
@mixin overflow{
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.color-red{
    color: $g-main-color;
}
.color-grey{
    color: $color-grey;
}
.color-black{
    color: #333;
}
.fs-14{
    font-size: 14px !important;
}
.pointer{
    cursor: pointer;
}

.fl{
    float: left;
}
.fr{
    float: right;
}
.overflow{
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.g-content-wrapper{
    width: 100%;
    margin-top: 20px;
    .g-content-box{
        @include g-page-content-row;
    }
}

.g-content-box{
    
    .g-content-right{
        float: left;
        width: 958px;
        margin-left: 40px;
        .top-title{
          height: 46px;
          line-height: 46px;
          background: #f8f8f8;
          border: 1px solid #dad8d8;
          font-size: 16px;
          padding-left: 20px;
      }
    }
        
    
}

.my-order-wrap{
    font-size: 13px;
}
.top-operate{
    padding: 15px 0 15px 20px;
    overflow: hidden;
    
    .noty-num{

        background-color: $g-main-color;
        color: #fff;
        border-radius: 100%;
        padding: 0px 3px;
        font-size: 12px;
        height: 17px;
        line-height: 17px;
        min-width: 11px;
        text-align: center;
    }
    
    .search-bar{
        float: right;
        vertical-align: middle;
        input{
            vertical-align: middle;
            height: 28px;
            line-height: 28px;
            border: solid 1px #ddd;
            width: 235px;
            padding: 0 5px;
            outline: none;
        }
        .search-pg{
            background: url(../../../assets/image/search.png) $g-main-color center center no-repeat;
            background-size: 14px 14px;
            width: 40px;
            height: 30px;
            cursor: pointer;
            display: inline-block;
            vertical-align: middle;
        }
    }
}

.order-table{
    border: $border1;
    .t-h{
        height: 32px;
        line-height: 32px;
        color: #666;
        font-weight: 400;
        text-align: center;
        padding: 0 5px;
    }

    .t-body{
        margin-bottom: 20px;
    }

    .h1{
        width:550px;
    }
    .h2{
        width:150px;
        text-align: center;
    }
    .h3{
        width:150px;
        text-align: center;
    }
    .h4{
        width:150px;
        text-align: center;
    }
    .h5{
        width:150px;
        text-align: center;
    }

    .t-info{
        padding-left: 5px;
        padding-right: 5px;
        height: 32px;
        line-height: 32px;
        background: #f8f8f8;
    }

    .order-time{
        color: $color-grey;
        margin-right: 35px;
    }
    .order-no{
        color: $color-grey;
        margin-left: 35px;
        span{
            color:#333;
        }
    }

    .item-info{
        width: 550px;
        .i-left{
            float: left;
        }
        .i-right{
            padding-top: 10px;
            float: left;
            margin-left: 20px;

            div {
                width: 400px;
                word-break:break-all;
            }
            .m-title{
                color: #333;
                font-size: 13px;
            }
            .other{
                color: #666;
                font-size: 12px;
                &.ml{
                    margin-left: 4px;
                }
            }
        }

        img{
            width: 64px;
            height: 64px;
        }
    }
    .o-btn{
        &:hover{
            color: $g-main-color;
            cursor: pointer;
        }
    }
}

// 无数据
.no-data{
    padding: 100px 0;
    text-align: center;
    color: #999;
}
.is-loading{
    padding: 100px 0;
    text-align: center;
}
// 取消订单
.cancelModel-content{
    text-align: left;
    padding-left: 30px;
    color: red;
    span{
            display: inline-block;
        vertical-align: top;
    }
}
//确认收货
.finishModel-content{
    font-size: 13px;
    color: #999;
    .p1{
        font-size: 18px;
        color: $g-main-color;
    }
    .tip{
        background-color: #faf6ea;
        line-height: 30px;
            text-indent: -156px;
    }
    table{
        width: 80%;
        margin: 15px auto;
        text-align: left;
        td{
            padding: 3px 13px;
        }
    }
    .name{
        @include overflow;
        width: 330px;
    }
}
.buyModel-content{
    .account{
        background-color: $g-main-color;
        border: solid 1px #ddd;
        padding: 4px 14px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 3px;
        margin-top: 20px;
        color: #fff;
        display: inline-block;
    }
}
.tag-item{
    padding: 4px 5px;
    cursor: pointer;
    margin-right: 10px;
    &.current{
        color: $g-main-color;
    }

}
</style>

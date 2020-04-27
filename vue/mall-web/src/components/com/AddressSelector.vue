<template>
    <div class="stock-address" 
          @mouseover="isShowAddressSelector = true" 
          @mouseleave=" _checkClose()">

        <div class="head">
          <span class="text">{{addressText}}</span> 
            <i class="fa" v-bind:class="{'fa-angle-down':!isShowAddressSelector,'fa-angle-up': isShowAddressSelector}"></i>
        </div>
        <div class="content" v-show="isShowAddressSelector">
          <div class="tab">选择新地址</div>
          <div class="address-select">
            <!-- 省市区标题栏 -->
            <ul class="address-tab">
              <li @click='_showProvinceList' :class="{active:isShowProviceList}">
                {{selectedProviceText}}
              </li>
              <li @click='_showCityList' :class="{active:isShowCityList}" v-show="isShowCityTab">
                {{selectedCityText}}
              </li>
              <li @click='_showDistrictList' :class="{active:isShowDistrictList}" v-show="isShowDistrictTab">
                {{selectedDistrictText}}
              </li>
            </ul>
            <div class="address-list">
              <!-- 具体地名 -->
              <ul v-show="isShowProviceList">
                <li v-for="item in provinceOptions" :key="item.code">
                  <a @click='_changeProvince(item)'>{{item.name}}</a>
                </li>
              </ul>
              <ul v-show="isShowCityList">
                <li v-for="item in cityOptions" :key="item.code">
                  <a @click='_changeCity(item)'>{{item.name}}</a>
                </li>
              </ul>
              <ul v-show="isShowDistrictList">
                <li v-for="item in districtOptions" :key="item.code">
                  <a @click='_changeDistrict(item)'>{{item.name}}</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
    </div>

</template>

<script>

import * as VueHelper from '@/helper/VueHelper'; 
import * as Ajax from '@/helper/Ajax'; 
import * as Urls from '@/helper/Urls';

// 地址选择组件
export default {
  name: 'AddressSelector',
  
  watch:{
    // provice(newValue, oldValue){
    //   this.provice = newValue;
    //   this.init();
    // },
    // city(newValue, oldValue){
    //   this.city = newValue;
    //   this.init();
    // },
    // district(newValue, oldValue){
    //   this.district = newValue;
    //   this.init();
    // }
  },

  computed: {

      // 选择的省份
      selectedProviceText(){
          let data = this.currentProvince;
          if(data && data.name){
              return data.name;
          }
          return '请选择'
      }, 

      // 选择的城市
      selectedCityText(){
          let data = this.currentCity;
          if(data && data.name){
              return data.name;
          }
          return '请选择'
      }, 

      // 选择的区域
      selectedDistrictText(){
          let data = this.currentDistrict;
          if(data && data.name){
              return data.name;
          }
          return '请选择'
      }, 

      addressText(){
          let p = this.currentProvince;
          let c = this.currentCity;
          let d = this.currentDistrict;

          if( p && p.name && c && c.name && d && d.name){
              return p.name + '/' + c.name + '/' + d.name;
          }

          return '--请选择--';
      }, 
  }, 

  data () {
    return {
        isShowAddressSelector:false,
        /** 
            结构和字段说明: 
            id 主键  name 名字 level 层级. children: 可能的子区域列表. 
         */
        provinceOptions: [
            // {code: 1, name:'广东省', level: 1, 
            //   children: [
            //     {code: 11, name: '广州市', level: 2,  parentCode: 1, 
            //       children: [
            //             {code: 111, name: '天河区', level: 3,  parentCode: 11, }, 
            //             {code: 112, name: '白云区', level: 3,  parentCode: 11, }, 
            //         ]},
            //     {code: 12, name: '深圳市', level: 2, parentCode: 1,  children: [
                
            //         {code: 121, name: '南山区', level: 3,  parentCode: 12, }, 
            //         {code: 122, name: '福田区', level: 3,  parentCode: 12, }, 
                    
            //     ]}, 
                
            // ]}, 
            
        ], 
        cityOptions: [], 
        districtOptions: [], 

        currentProvince: {
            code: '', 
            name: '', 
        }, 
        currentCity: {

        }, 
        currentDistrict: {

        }, 

        isShowProviceList: true,//省列表
        isShowCityList: false,//城市列表
        isShowDistrictList: false,//地区列表

        isShowCityTab:false,//是否显示城市标题栏
        isShowDistrictTab:false,//是否显示地区标题栏

        
    }
  },
  methods: {

      _checkClose(){
        
          let showC = this.isShowCityList;
          let showD = this.isShowDistrictList;

          // 如果只是鼠标移走就关闭菜单, 那么当省份太多的时候就有个bug. 
          this.isShowAddressSelector = showC || showD;
      }, 
    
      _showProvinceList:function(){
          this.isShowProviceList=true;
          this.isShowCityList=false;
          this.isShowDistrictList=false;
      },
      _showCityList:function(){
          this.isShowProviceList=false;
          this.isShowCityList=true;
          this.isShowDistrictList=false;

      },
      _showDistrictList:function(){
          this.isShowProviceList=false;
          this.isShowCityList=false;
          this.isShowDistrictList=true;

      },

      _changeProvince:function(item){
          this.currentProvince = item;
          this.currentCity = {};
          this.currentDistrict = {};
          this.isShowCityTab = true;
          this.isShowDistrictTab = false;
          this._showCityList();

          this.provinceOptions.forEach(province => {
              if(province.code == item.code){
                  this.cityOptions = province.children;
              }
          });

      },
      _changeCity:function(item){
        this.currentCity = item;
        this.currentDistrict = {};
        this.isShowDistrictTab = true;
        this._showDistrictList();

        this.cityOptions.forEach(city => {
            if(city.code == item.code){
                this.districtOptions = city.children;
            }
        });

      },
      _changeDistrict:function(item){
          this.currentDistrict = item;
          this.isShowProviceList=false;
          this.isShowCityList=false;
          this.isShowDistrictList=false;

          this.$emit('finishSelect', {
              'provinceCode': this.currentProvince.code, 
              'cityCode': this.currentCity.code, 
              'districtCode': this.currentDistrict.code, 
              'provinceName': this.currentProvince.name, 
              'cityName': this.currentCity.name, 
              'districtName': this.currentDistrict.name, 
          });
          
        // this.$emit('provice',this.selectedProviceText);
        // this.$emit('city',this.selectedCityText);
        // this.$emit('district',this.selectedDistrictText);
        // this.$emit('proviceId',this.proviceId);
        // this.$emit('cityId',this.cityId);
        // this.$emit('districtId',this.districtId);
      },

      // 由外部控件调用
      getData(){
          return {
              'districtCode': this.currentDistrict.code, 
              'districtName': this.currentDistrict.name, 
              'cityCode': this.currentCity.code, 
              'cityName': this.currentCity.name, 
              'provinceCode': this.currentProvince.code, 
              'provinceName': this.currentProvince.name, 
          }
      }, 

      // 外部数据初始化, 传入 districtCode
      setDataCode(districtCode){

      },

      // 外部数据初始化, 传入 完整的 districtVo 对象
      setDataVo(voData){

          this.currentProvince.code = voData.provinceCode;
          this.currentProvince.name = voData.provinceName;
          this.provinceOptions = [this.currentProvince];

          this.currentCity.code = voData.cityCode;
          this.currentCity.name = voData.cityName;
          this.cityOptions = [this.currentCity];

          this.currentDistrict.code = voData.districtCode;
          this.currentDistrict.name = voData.districtName;
          this.districtOptions = [this.currentDistrict];

          this.init();
      },

      // 获取城市数据, 并将之保存在缓存
      init(){

          let url = Urls.common_getProvinceList;

          let params = {};

          let inst = this;
          Ajax.post(url, params, function(serverData){
              inst.provinceOptions = serverData.data;
          });
      }, 
  }, 
  mounted(){
    
  }, 

}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';

    .stock-address{
      position: relative;
      display: inline-block;
      font-size: 13px;
      border: solid 1px #fff;
      z-index:100;
      cursor: pointer;
      &:hover{
        border: solid 1px #ccc;
        border-bottom: solid 1px #fff;
      }
      .head{
        height: 24px;
        line-height: 24px;
        background-color: #fff;
        padding: 0 5px;
        
      }
      .content{
        background-color: #fff;
        position: absolute;
        border: solid 1px #ccc;
        top: 23px;
        left: -1px;
        z-index: -1;
        padding: 20px 10px;
        width: 548px;
        .tab{
          font-weight: bold;
          margin-bottom: 10px;
        }
        .address-tab{
          li{
              display: inline-block;
              border: solid 1px #ccc;
              padding: 2px 10px;
              margin-right: 5px;
              border-bottom: 0 none;
              color: #005aa0;
              font-weight:bold;
            &.active{
              border: 2px solid #e4393c;
              border-bottom: solid 2px #fff;
              background-color: #fff;
            }
          }
        }
        .address-list{
          border-top: 2px solid #e4393c;
          margin-top: -2px;
          padding: 10px 0;
          li{
            min-width: 130px;
            margin-right: 5px;
            margin-bottom: 5px;
            display:inline-block;
            a{
              padding:0 2px;
              &:hover{
              background-color: #e4393c;
              color:#fff;
              }
            }
          }
        }

      }
    }

</style>

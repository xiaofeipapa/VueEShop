<template>
  <!-- 顶部分类 这一整横栏 -->
  <div class="g-all-cat-wrapper">
    <div class="g-category">
      <div
        class="category-item"
        v-for="item in topCats"
      >{{item.label}}</div>
    </div>
    <div class="clear-both"></div>
    <div class="g-category-box">
      <div class="g-category-content" @mouseleave="hideMenu()">
        <!-- 左侧分类 -->
        <div class="cat-left">
          <div
            class="cat-row"
            :key="'leftcat-' + item.id"
            v-for="item in catList"
            @mouseover="checkShowRight($event,item.id)"
          >{{item.name}}</div>
        </div>
        <!-- 右侧分类内容 -->
        <div class="cat-right" :style="'left:' + floatingMenuX + 'px;'" v-show="isShowFloatingMenu">
          <div class="cat-content">
            <div class="content-row" v-for="rightItem in catContentItems">
              <div class="cat-label">
                <div
                  class="cat-text"
                >{{rightItem.name}}</div>
              </div>
              <div class="cat-item">
                <div class="item" v-for="childItem in rightItem.children">
                  <div
                    class="item-text"
                    @click="_searchByCat(childItem.id)"
                  >{{childItem.name}}</div>
                </div>
              </div>
              <div class="clear-both"></div>
              <div class="row-sep"></div>
            </div>
          </div>
        </div>

        <!-- 右侧轮播图 -->
        <div class="swiper-right">
          <swiper :options="swiperOption">
            <swiper-slide
              class="swiper-slide"
              v-for="(item,index) in slideList"
              :style="'background-image:url(' + item.imageUrl + ');'"
              :key="index"
            > {{item.name}}</swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
            <!-- 分页 -->
            <div class="swiper-button-prev" slot="button-prev"></div>
            <!-- 箭头左 -->
            <div class="swiper-button-next" slot="button-next"></div>
            <!-- 箭头右 -->
          </swiper>
        </div>
      </div>
    </div>
    <div class="clear-both"></div>
  </div>
</template>

<script>
// ------ 顶部大分类 和轮播图

import * as Ajax from "@/helper/Ajax";
import * as VueHelper from "@/helper/VueHelper";
import * as Urls from "@/helper/Urls";

// 使用轮播图
import "swiper/dist/css/swiper.css";
import { swiper, swiperSlide } from "vue-awesome-swiper";

export default {
  props: [],
  components: {
    swiper,
    swiperSlide
  },
  data() {
    return {
      floatingMenuX: 0,
      isShowFloatingMenu: false, // 是否显示浮动的分类菜单
      topCats: [],
      showingCatId: "", // 正在显示的左侧分类id
      catContentItems: [], // 点击左侧的分类, 此数组的内容会改变

      slideList: [1, 2, 3, 4],

      catList: [
        // {
        //   id: 100,
        //   name: "分类1",
        //   children: [
        //     {
        //       id: 200,
        //       name: "子分类1",
        //       children: [
        //         { id: 1000, name: "孙子分类1" },
        //         { id: 1001, name: "孙子分类2" }
        //       ]
        //     },
        //     {
        //       id: 201,
        //       name: "子分类2",
        //       children: [
        //         { id: 1002, name: "孙子分类3" },
        //         { id: 1003, name: "孙子分类4" }
        //       ]
        //     }
        //   ]
        // },
        // { id: 101, name: "分类2", children: [] },
        // { id: 102, name: "分类3", children: [] },
        // { id: 103, name: "分类4", children: [] },
        // { id: 104, name: "分类5", children: [] },
        // { id: 105, name: "分类6", children: [] },
        // { id: 106, name: "分类7", children: [] },
        // { id: 107, name: "分类8", children: [] }
      ], // 分类列表

      swiperOption: {
        //显示分页
        pagination: {
          el: ".swiper-pagination",
          clickable: true //允许分页点击跳转
        },
        //设置点击箭头
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev"
        },
        //自动轮播
        autoplay: {
          delay: 3000
        },
        //开启循环模式
        loop: true,
        //开启鼠标滚轮控制Swiper切换
        mousewheel: true
      }
    };
  },
  //在挂载开始之前被调用
  beforeMount() {

      this._loadData();
  },

  methods: {
    _loadData() {
        let inst = this;

        let url = Urls.homeIndex_getNavData;
        let params = {};

        Ajax.post(url, params, function(serverData) {
            let data = serverData.data;
            inst.slideList = data.adList;
            inst.catList = data.catList;
        });
    },

    checkShowRight: function(event, catId) {
      //   console.log('-------event: ' , event);
      this.showingCatId = catId;
      let theCat = VueHelper.getItemById(this.catList, catId);
      this.catContentItems = theCat.children;

      // 决定右侧菜单的x坐标
      let rect = event.target.getBoundingClientRect(); //获取点击的dom的位置

      //   console.log('--- rect : ', rect);
      this.floatingMenuX = rect.width;
      this.isShowFloatingMenu = true;
    },

    showMenu: function(event) {
      this.isShowFloatingMenu = true;
      //   this.checkShowRight(this.topCats[0].id);
    },

    hideMenu: function() {
      this.isShowFloatingMenu = false;
      this.showingCatId = "";
    },

    lsLeftHover: function(catId) {
      return this.showingCatId === catId;
    },
    _searchByCat(id) {
        window.open("/views/product/index.html?c=" + id);
    }
  },
  computed: {}
};
</script>

<!-- 页面样式设置 -->
<style lang="scss" scoped type="text/css">
@import "../../assets/scss/themeDefault.scss";

$color-background: #f8f8f8;

// ================ 顶部分类 ===============

.g-all-cat-wrapper {
  background-color: #fff;
  width: 100%;
  height: 36px;
  position: relative;
  z-index: 9;
}
.g-category {
  width: $g-main-width;
  margin: 0 auto;
  height: 36px;
  line-height: 36px;

  .category-item {
    cursor: pointer;
    text-align: center;
    float: left;
    color: #333;
    margin-left: 65px;
    border-bottom: 2px solid $g-main-color;
    i {
      margin-left: 5px;
    }
    &:hover {
      color: $g-main-color;
    }
  }
  .category-item.red {
    background: $g-main-color;
    width: 200px;
    color: #fff;
    margin-left: 0;
    margin-right: 25px;
  }
}

/** 用于将分类内容居中 **/
.g-category-box {
  width: $g-main-width;
  margin: 0 auto;
  position: relative;
  margin-top: 10px;
}

/** 用于将分类内容设置成浮动在其他层上方 **/
.g-category-content {
  z-index: 10;
  // display: table;
  .cat-left {
    width: 200px;
    height: 480px;
    overflow: hidden;
    float: left;
    .cat-row {
      font-size: 14px;
      position: relative;
      padding: 10px 30px;
      overflow: hidden;

      &:hover {
        background: #eaeaea;
        color: $g-main-color;
        cursor: pointer;
      }
    }
  }
  .cat-right {
    // display: table-cell;
    float: left;
    width: 950px;
    color: #333;
    box-shadow: 0px 0px 10px #c4c4c4;

    z-index: 1000;
    position: absolute;
    background: #fff;

    .row-sep {
      margin-left: 35px;
    }
    .cat-content {
      float: left;
      width: 710px;
      padding-bottom: 40px;
    }
    .content-row {
      width: 100%;
      font-size: 13px;
      .cat-label {
        width: 102px;
        display: table-cell;
        vertical-align: middle;
        .cat-text {
          margin: 0 auto;
          font-weight: bold;
          padding: 10px 12px;
          text-align: center;
          cursor: pointer;
        }
      }
      .cat-item {
        display: table-cell;
      }
      .item {
        margin: 10px 5px 10px 2px;
        padding: 0px 5px 0px 0px;
        float: left;

        .item-text {
          float: left;
          cursor: pointer;
          &:hover {
            color: $g-main-color;
          }
        }
      }
    }
  }

  .swiper-right {
    float: left;
    width: 900px;
  }
  .swiper-slide {
    height: 450px;
    font-size: 50px;
    text-align: center;
    line-height: 450px;
  }
  .swiper-button-next {
    color: #999;
  }
}
</style>

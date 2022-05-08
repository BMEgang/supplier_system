import Vue from "vue";
import VueRouter from "vue-router";
import login from '/Users/ganghu/Desktop/es6/mocks-demo/mxg-msm/src/views/login/index.vue'
import layout from '@/components/layout.vue'
import Home from '/Users/ganghu/Desktop/es6/mocks-demo/mxg-msm/src/views/home/index.vue'
import Member from '/Users/ganghu/Desktop/es6/mocks-demo/mxg-msm/src/views/member/index.vue'
import Staff from '/Users/ganghu/Desktop/es6/mocks-demo/mxg-msm/src/views/staff/index.vue'
import Supplier from '/Users/ganghu/Desktop/es6/mocks-demo/mxg-msm/src/views/supplier/index.vue'
import Goods from '/Users/ganghu/Desktop/es6/mocks-demo/mxg-msm/src/views/goods/index.vue'
// import * as path from "path";

Vue.use(VueRouter);

const router = new VueRouter({
  routes:[
      {
          path: '/login',
          name: 'login',
          component: login
      },
      {
          path: '/',
          name: 'layout',
          component: layout,
          redirect: '/home',
          children: [
              {
                  path: '/home',
                  component: Home,
                  meta: {
                      title: 'Main page'
                  }
              }
          ]
      },
      {
          path: '/member',
          component: layout,
          children: [
              {
                  path: '/',
                  component: Member,
                  meta: {
                      title: 'Member page'
                  }
              }
          ]

      },
      {
          path: '/staff',
          component: layout,
          children: [
              {
                  path: '/',
                  component: Staff,
                  meta: {
                      title: 'Staff page'
                  }
              }
          ]

      },
      {
          path: '/supplier',
          component: layout,
          children: [
              {
                  path: '/',
                  component: Supplier,
                  meta: {
                      title: 'Supplier page'
                  }
              }
          ]

      },
      {
          path: '/goods',
          component: layout,
          children: [
              {
                  path: '/',
                  component: Goods,
                  meta: {
                      title: 'Goods page'
                  }
              }
          ]

      }
  ]
});

export default router;

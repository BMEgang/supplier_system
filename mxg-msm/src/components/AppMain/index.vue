<template>
  <div>
    <div class="main">

      <Applink></Applink>

      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Applink from './link.vue'
export default {
  components: {Applink}
}
</script>
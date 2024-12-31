<template>
  <div :class="['app-header', { hidden: hideHeader }]" ref="header" key="$route.path">
    <div >
      <router-link to="/landing">
    	<section class="section-1">
    		<div class="title">
    			<span data-desktop-color="rgba(0,97,249,1)" style="color: rgb(0, 97, 249);">Cloudforce</span>
			</div>
			<div class="subtitle">
				</div>
		</section>
      </router-link>
    </div>
    <!-- Add a button for the hamburger icon -->
    <div class="hamburger" >
	  <a-menu v-model:selectedKeys="current" mode="horizontal">
	    <a-sub-menu>
	      <template #icon>
	        <setting-outlined />
	      </template>
	      <template #title><div class="hamburger" >&#9776</div></template>
											<a-menu-item key="setting:1"><router-link class="link" to="/accounts">My Accounts</router-link></a-menu-item>
														            <a-menu-item key="setting:1"><router-link class="link" to="/pricing">Pricing</router-link></a-menu-item>

	    </a-sub-menu>
	  </a-menu>
    </div>

    <div :class="['links', { 'links--active': isMenuOpen }]">
	
		<nav class="nav">
								        <router-link class="link" to="/accounts">My Accounts</router-link>
            
													        <router-link class="link" to="/pricing">Pricing</router-link>
        <router-link class="link" to="/users">Menu-item-2</router-link>
        <router-link class="link" to="/sign-in">Menu-item-3</router-link>
        <router-link class="link" to="/sign-in">Menu-item-4</router-link>
      </nav>
	
    </div>
  </div>
</template>

<script>
export default {
  name: "AppHeader",
  props: ["hideHeader"],
  data() {
    return {
	  current: null,
      isMenuOpen: false,
    };
  },
  computed: {
    isSignInPage() {
      // Check if the current route is '/sign-in'
      return this.$route.path === '/sign-in';
    },
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    },
  },
};
</script>

<style scoped>
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  box-shadow: 0px -10px 23px rgba(51, 51, 51, 0.2);
  position: fixed;
  width: 100%;
  top: 0;
  background: #fff;
  flex-wrap: wrap;
  height: 80px; 
  transition: height 0.3s ease; 
}

.logo {
  height: 70px;
  cursor: pointer;
}

.links {
  display: flex;
  align-items: center;
}

.avator img {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  border: 1px solid #ccc;
}

.hamburger {
  display: none;
  font-size: 30px;
  cursor: pointer;
}

/* Popup styling */
.menu-popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
}

.popup-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  width: 80%;
  max-width: 300px;
}

.close {
  position: absolute;
  top: 10px;
  right: 15px;
  font-size: 30px;
  cursor: pointer;
}

.popup-link {
  color: #444;
  text-transform: uppercase;
  text-decoration: none;
  font-size: 18px;
  display: block;
  margin-top: 20px;
}

.popup-link:hover {
  text-decoration: underline;
  color: #0000ff;
}


/* Media queries to handle responsiveness */
@media (max-width: 768px) {
  .links {
    display: none;
    flex-direction: column;
    width: 100%;
    text-align: center;
  }
  .links--active {
    display: flex;
  }
  .hamburger {
    display: block;
  }
}

.link {
  margin-right: 1rem;
  color: #444;
/* text-transform: uppercase; */
  text-decoration: none;
  font-size: 14px;
}

.link:hover {
  color: #0000ff;
}

.link {
  margin-right: 1.5rem;
  color: #444;
/* text-transform: uppercase; */
  text-decoration: none;
  font-weight: bold;
  font-size: 14px;
}

.link:hover {
  color: #007bff;
}

.section-1 {
  line-height: 34px;
  background-color: white;
  color: rgb(0, 97, 249);
  text-align: center;
  font-weight: 500;
  opacity: 1;
  font-size: 42px;
  font-style: italic;
}

.section-1 .subtitle {
  background-color: white;
  color: rgb(0, 97, 249);
  text-align: center;
  font-weight: 400;
  opacity: 1;
  font-size: 21px;
  font-style: italic;
}

</style>

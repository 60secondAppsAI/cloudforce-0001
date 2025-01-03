import Vue from "vue";
import VueRouter from "vue-router";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import Leads from  '@/pages/Leads.vue';
import LeadDetail from  '@/pages/LeadDetail.vue';
import Reports from  '@/pages/Reports.vue';
import ReportDetail from  '@/pages/ReportDetail.vue';
import Permissions from  '@/pages/Permissions.vue';
import PermissionDetail from  '@/pages/PermissionDetail.vue';
import Users from  '@/pages/Users.vue';
import UserDetail from  '@/pages/UserDetail.vue';
import Events from  '@/pages/Events.vue';
import EventDetail from  '@/pages/EventDetail.vue';
import Opportunitys from  '@/pages/Opportunitys.vue';
import OpportunityDetail from  '@/pages/OpportunityDetail.vue';
import PriceBookEntrys from  '@/pages/PriceBookEntrys.vue';
import PriceBookEntryDetail from  '@/pages/PriceBookEntryDetail.vue';
import Accounts from  '@/pages/Accounts.vue';
import AccountDetail from  '@/pages/AccountDetail.vue';
import Contacts from  '@/pages/Contacts.vue';
import ContactDetail from  '@/pages/ContactDetail.vue';
import Dashboards from  '@/pages/Dashboards.vue';
import DashboardDetail from  '@/pages/DashboardDetail.vue';
import EmailTemplates from  '@/pages/EmailTemplates.vue';
import EmailTemplateDetail from  '@/pages/EmailTemplateDetail.vue';
import LoginHistorys from  '@/pages/LoginHistorys.vue';
import LoginHistoryDetail from  '@/pages/LoginHistoryDetail.vue';
import Groups from  '@/pages/Groups.vue';
import GroupDetail from  '@/pages/GroupDetail.vue';
import Roles from  '@/pages/Roles.vue';
import RoleDetail from  '@/pages/RoleDetail.vue';
import Activitys from  '@/pages/Activitys.vue';
import ActivityDetail from  '@/pages/ActivityDetail.vue';
import PriceBooks from  '@/pages/PriceBooks.vue';
import PriceBookDetail from  '@/pages/PriceBookDetail.vue';
import Products from  '@/pages/Products.vue';
import ProductDetail from  '@/pages/ProductDetail.vue';
import Tasks from  '@/pages/Tasks.vue';
import TaskDetail from  '@/pages/TaskDetail.vue';
import Campaigns from  '@/pages/Campaigns.vue';
import CampaignDetail from  '@/pages/CampaignDetail.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("../views/HomeView.vue"),
										redirect: '/accounts',
												  },
 


	{
		path: '/leads',
		name: 'Leads',
		layout: DefaultLayout,
		component: Leads,
	},
	{
	    path: '/lead/:leadId', 
	    name: 'LeadDetail',
		layout: DefaultLayout,
	    component: LeadDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/reports',
		name: 'Reports',
		layout: DefaultLayout,
		component: Reports,
	},
	{
	    path: '/report/:reportId', 
	    name: 'ReportDetail',
		layout: DefaultLayout,
	    component: ReportDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/permissions',
		name: 'Permissions',
		layout: DefaultLayout,
		component: Permissions,
	},
	{
	    path: '/permission/:permissionId', 
	    name: 'PermissionDetail',
		layout: DefaultLayout,
	    component: PermissionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/users',
		name: 'Users',
		layout: DefaultLayout,
		component: Users,
	},
	{
	    path: '/user/:userId', 
	    name: 'UserDetail',
		layout: DefaultLayout,
	    component: UserDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/events',
		name: 'Events',
		layout: DefaultLayout,
		component: Events,
	},
	{
	    path: '/event/:eventId', 
	    name: 'EventDetail',
		layout: DefaultLayout,
	    component: EventDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/opportunitys',
		name: 'Opportunitys',
		layout: DefaultLayout,
		component: Opportunitys,
	},
	{
	    path: '/opportunity/:opportunityId', 
	    name: 'OpportunityDetail',
		layout: DefaultLayout,
	    component: OpportunityDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/priceBookEntrys',
		name: 'PriceBookEntrys',
		layout: DefaultLayout,
		component: PriceBookEntrys,
	},
	{
	    path: '/priceBookEntry/:priceBookEntryId', 
	    name: 'PriceBookEntryDetail',
		layout: DefaultLayout,
	    component: PriceBookEntryDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/accounts',
		name: 'Accounts',
		layout: DefaultLayout,
		component: Accounts,
	},
	{
	    path: '/account/:accountId', 
	    name: 'AccountDetail',
		layout: DefaultLayout,
	    component: AccountDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/contacts',
		name: 'Contacts',
		layout: DefaultLayout,
		component: Contacts,
	},
	{
	    path: '/contact/:contactId', 
	    name: 'ContactDetail',
		layout: DefaultLayout,
	    component: ContactDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/dashboards',
		name: 'Dashboards',
		layout: DefaultLayout,
		component: Dashboards,
	},
	{
	    path: '/dashboard/:dashboardId', 
	    name: 'DashboardDetail',
		layout: DefaultLayout,
	    component: DashboardDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/emailTemplates',
		name: 'EmailTemplates',
		layout: DefaultLayout,
		component: EmailTemplates,
	},
	{
	    path: '/emailTemplate/:emailTemplateId', 
	    name: 'EmailTemplateDetail',
		layout: DefaultLayout,
	    component: EmailTemplateDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/loginHistorys',
		name: 'LoginHistorys',
		layout: DefaultLayout,
		component: LoginHistorys,
	},
	{
	    path: '/loginHistory/:loginHistoryId', 
	    name: 'LoginHistoryDetail',
		layout: DefaultLayout,
	    component: LoginHistoryDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/groups',
		name: 'Groups',
		layout: DefaultLayout,
		component: Groups,
	},
	{
	    path: '/group/:groupId', 
	    name: 'GroupDetail',
		layout: DefaultLayout,
	    component: GroupDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/roles',
		name: 'Roles',
		layout: DefaultLayout,
		component: Roles,
	},
	{
	    path: '/role/:roleId', 
	    name: 'RoleDetail',
		layout: DefaultLayout,
	    component: RoleDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/activitys',
		name: 'Activitys',
		layout: DefaultLayout,
		component: Activitys,
	},
	{
	    path: '/activity/:activityId', 
	    name: 'ActivityDetail',
		layout: DefaultLayout,
	    component: ActivityDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/priceBooks',
		name: 'PriceBooks',
		layout: DefaultLayout,
		component: PriceBooks,
	},
	{
	    path: '/priceBook/:priceBookId', 
	    name: 'PriceBookDetail',
		layout: DefaultLayout,
	    component: PriceBookDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/products',
		name: 'Products',
		layout: DefaultLayout,
		component: Products,
	},
	{
	    path: '/product/:productId', 
	    name: 'ProductDetail',
		layout: DefaultLayout,
	    component: ProductDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/tasks',
		name: 'Tasks',
		layout: DefaultLayout,
		component: Tasks,
	},
	{
	    path: '/task/:taskId', 
	    name: 'TaskDetail',
		layout: DefaultLayout,
	    component: TaskDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/campaigns',
		name: 'Campaigns',
		layout: DefaultLayout,
		component: Campaigns,
	},
	{
	    path: '/campaign/:campaignId', 
	    name: 'CampaignDetail',
		layout: DefaultLayout,
	    component: CampaignDetail,
	    props: true // Pass route params as props to the component
  	},
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes,
});

export default router;

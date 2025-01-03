
<template>
  <div class="content">
    <!-- search bar -->
    <div class="row my-3 justify-content-end">
      <div class="col-8"></div>
       <div class="col-auto">
        <!-- Header Search Input -->
        <a-input-search class="header-search" :class="searchLoading ? 'loading' : ''" placeholder="Search by color, sponsor, or style...."
          @search="onSearch" :loading='searchLoading' v-model="searchQuery">
          <svg slot="prefix" width="16" height="16" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M8 4C5.79086 4 4 5.79086 4 8C4 10.2091 5.79086 12 8 12C10.2091 12 12 10.2091 12 8C12 5.79086 10.2091 4 8 4ZM2 8C2 4.68629 4.68629 2 8 2C11.3137 2 14 4.68629 14 8C14 9.29583 13.5892 10.4957 12.8907 11.4765L17.7071 16.2929C18.0976 16.6834 18.0976 17.3166 17.7071 17.7071C17.3166 18.0976 16.6834 18.0976 16.2929 17.7071L11.4765 12.8907C10.4957 13.5892 9.29583 14 8 14C4.68629 14 2 11.3137 2 8Z"
              fill="#111827" />
          </svg>
        </a-input-search>
        <!-- / Header Search Input -->
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <div class="row justify-content-between align-items-between mx-3">

              <h5 class="card-title">Accounts</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalAccounts = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalAccounts">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add Account</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="AccountId" type="text" placeholder="Enter AccountId" v-model="accountToAdd.accountId"></base-input>
  <base-input label="AccountName" type="text" placeholder="Enter AccountName" v-model="accountToAdd.accountName"></base-input>
  <base-input label="AccountType" type="text" placeholder="Enter AccountType" v-model="accountToAdd.accountType"></base-input>
  <base-input label="Industry" type="text" placeholder="Enter Industry" v-model="accountToAdd.industry"></base-input>
                  </form>
                </div>
                <template slot="footer">
                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
                </template>
              </modal>
            </div>
          </template>


          <!-- Conditionally render the view based on the 'isTableView' flag -->
          <div v-if="isTableView">
            <!-- Render the existing Table View -->
            <a-table :columns="columns" :data-source="accounts" :row-key="record => record.AccountId" :pagination="pagination"
              :loading="searchLoading" @change="onTableChange" :scroll="{ x: 'max-content' }">



             <template slot="lastModified" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="createdOn" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="blackOutStartDate" slot-scope="dataIndex">
              	{{ formatDate(dataIndex) }}
              </template>
            </a-table>
          </div>
          <div v-else>
            <!-- Render the Picture View  -->
            <AccountPictureView :accounts="accounts" />
          </div>

        </card>
      </div>
    </div>

  </div>
</template>

<script>
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/Card";
import AccountService from "../services/AccountService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import AccountPictureView from './AccountPictureView.vue';


const accountsColumns = [
  "accountId",
  "year",
  "date",
  "competitionId",
  "accountId"
]

export default {
  props: {
    accounts: {
      type: Array,
      required: true,
    },
    totalElements: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
  },
  components: {
    Card,
    Modal,
    BaseButton,
    BaseInput,
    Table,

    Pagination,
    AccountPictureView
  },

  data() {
    return {
      modalAccounts: false,
        isTableView: true,

      columns: [
        {
          title: 'Account Id',
		dataIndex: 'accountId',
          visible: true,
          scopedSlots: { customRender: 'accountId' },
          sorter: true
          //sorter: (a, b) => a.accountId - b.accountId,
          //sorter: (a, b) => a.accountId.localeCompare(b.accountId),
        },
        {
          title: 'Account Name',
		dataIndex: 'accountName',
          visible: true,
          scopedSlots: { customRender: 'accountName' },
          sorter: true
          //sorter: (a, b) => a.accountName - b.accountName,
          //sorter: (a, b) => a.accountName.localeCompare(b.accountName),
        },
        {
          title: 'Account Type',
		dataIndex: 'accountType',
          visible: true,
          scopedSlots: { customRender: 'accountType' },
          sorter: true
          //sorter: (a, b) => a.accountType - b.accountType,
          //sorter: (a, b) => a.accountType.localeCompare(b.accountType),
        },
        {
          title: 'Industry',
		dataIndex: 'industry',
          visible: true,
          scopedSlots: { customRender: 'industry' },
          sorter: true
          //sorter: (a, b) => a.industry - b.industry,
          //sorter: (a, b) => a.industry.localeCompare(b.industry),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} accounts`,
      },

      accounts: [],
      accountToAdd: {},

      accountsTable: {
        columns: [...accountsColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'accountId',           // Column to sort by
      sortOrder: 'asc',     // Sort order (asc or desc)
      searchQuery: '',      // Search query
      searchLoading: false, // Initialize searchLoading property


    };
  },
  watch: {
    searchQuery: {
      handler: "handleSearchQueryChanged", // Call the fetchData method when searchQuery changes
      immediate: true, // Trigger immediately when the component is mounted
    },
  },

  methods: {

    async renderAccountsTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let accountsTableData = [];
      for (let i = 0; i < this.accounts.length; i++) {
        accountsTableData.push({
          id: i,
          accountId: this.accounts[i].accountId,
          year: this.accounts[i].year,
          date: this.accounts[i].date,
          competitionId: this.accounts[i].competitionId,
          accountId: this.accounts[i].accountId,
        });

      }
      this.searchLoading = false;
    },

    async onTableChange(pagination, filters, sorter) {
      if (sorter && sorter.field && sorter.order) {
        this.sortBy = sorter.field;
        if (sorter.order == "ascend") {
            this.sortOrder = "asc";
        } else {
            this.sortOrder = "desc";
        }
      }
      if (pagination) {
        this.pagination.current = pagination.current;
        this.pagination.pageSize = pagination.pageSize;
      }

	  this.$emit('get-all-accounts',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
      this.handleTableChanged()
    },
	
	initializeColumns() {
        this.columns = this.columns.filter(item => item.visible);
    },

    routingToLeadDetail(id) {
      this.$router.push({ name: 'LeadDetail', params: { leadId: id.toString() }})
    },
    routingToReportDetail(id) {
      this.$router.push({ name: 'ReportDetail', params: { reportId: id.toString() }})
    },
    routingToPermissionDetail(id) {
      this.$router.push({ name: 'PermissionDetail', params: { permissionId: id.toString() }})
    },
    routingToUserDetail(id) {
      this.$router.push({ name: 'UserDetail', params: { userId: id.toString() }})
    },
    routingToEventDetail(id) {
      this.$router.push({ name: 'EventDetail', params: { eventId: id.toString() }})
    },
    routingToOpportunityDetail(id) {
      this.$router.push({ name: 'OpportunityDetail', params: { opportunityId: id.toString() }})
    },
    routingToPriceBookEntryDetail(id) {
      this.$router.push({ name: 'PriceBookEntryDetail', params: { priceBookEntryId: id.toString() }})
    },
    routingToAccountDetail(id) {
      this.$router.push({ name: 'AccountDetail', params: { accountId: id.toString() }})
    },
    routingToContactDetail(id) {
      this.$router.push({ name: 'ContactDetail', params: { contactId: id.toString() }})
    },
    routingToDashboardDetail(id) {
      this.$router.push({ name: 'DashboardDetail', params: { dashboardId: id.toString() }})
    },
    routingToEmailTemplateDetail(id) {
      this.$router.push({ name: 'EmailTemplateDetail', params: { emailTemplateId: id.toString() }})
    },
    routingToLoginHistoryDetail(id) {
      this.$router.push({ name: 'LoginHistoryDetail', params: { loginHistoryId: id.toString() }})
    },
    routingToGroupDetail(id) {
      this.$router.push({ name: 'GroupDetail', params: { groupId: id.toString() }})
    },
    routingToRoleDetail(id) {
      this.$router.push({ name: 'RoleDetail', params: { roleId: id.toString() }})
    },
    routingToActivityDetail(id) {
      this.$router.push({ name: 'ActivityDetail', params: { activityId: id.toString() }})
    },
    routingToPriceBookDetail(id) {
      this.$router.push({ name: 'PriceBookDetail', params: { priceBookId: id.toString() }})
    },
    routingToProductDetail(id) {
      this.$router.push({ name: 'ProductDetail', params: { productId: id.toString() }})
    },
    routingToTaskDetail(id) {
      this.$router.push({ name: 'TaskDetail', params: { taskId: id.toString() }})
    },
    routingToCampaignDetail(id) {
      this.$router.push({ name: 'CampaignDetail', params: { campaignId: id.toString() }})
    },
    
    handleSearchQueryChanged() {
    	console.log("handleSearchQueryChanged CALLED FROM @search")
    	this.$root.$emit('searchQueryForAccountsChanged', this.searchQuery);
		//this.renderAccountsTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalAccounts = false;

      const currentDate = new Date().getTime();
      this.accountToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.accountToAdd);
      console.log(jsonData);
      
      const res = await AccountService.addAccount(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},
	
	handleTableChanged() {
	},
	
	formatTime(dateString) {
      if(dateString !== null){
        const date = new Date(dateString);
      return `${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}-${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')} EST`;
      }
      // Format the date here as needed, for example:
    },  
    
 formatDate(dateString) {
    if (dateString !== null) {
	    const date = new Date(dateString);
	    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    const day = String(date.getDate()).padStart(2, '0');
	    const monthAbbreviation = months[date.getMonth()];
	    const year = date.getFullYear();
	    return `${day} ${monthAbbreviation} ${year}`;
  	}
  	// Handle the case when dateString is null or invalid
  	return '';
    
   },

  },
  mounted() {
  	this.initializeColumns();
    this.renderAccountsTable();
  }
};
</script>

<style>
.modal-dialog {
  margin-top: -300px;
}
.ant-table-row  {
	text-align: center;
}
.ant-table-thead th span {
	text-align: center;
	width: 100%
}
.ant-table-fixed td span {
    text-align: center;
}
.header-search {
  width: 300px !important;
  margin-left: auto !important;
}
</style>

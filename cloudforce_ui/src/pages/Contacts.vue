<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <contact-table
            v-if="contacts && contacts.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:contacts="contacts"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-contacts="getAllContacts"
             >

            </contact-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ContactTable from "@/components/ContactTable";
import ContactService from "../services/ContactService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ContactTable,
  },
  data() {
    return {
      contacts: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllContacts(sortBy='contactId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ContactService.getAllContacts(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.contacts.length) {
					this.contacts = response.data.contacts;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching contacts:", error);
        }
        
      } catch (error) {
        console.error("Error fetching contact details:", error);
      }
    },
  },
  mounted() {
    this.getAllContacts();
  },
  created() {
    this.$root.$on('searchQueryForContactsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllContacts();
    })
  }
};
</script>
<style></style>

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
            <role-table
            v-if="roles && roles.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:roles="roles"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-roles="getAllRoles"
             >

            </role-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import RoleTable from "@/components/RoleTable";
import RoleService from "../services/RoleService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    RoleTable,
  },
  data() {
    return {
      roles: [],
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
    async getAllRoles(sortBy='roleId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await RoleService.getAllRoles(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.roles.length) {
					this.roles = response.data.roles;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching roles:", error);
        }
        
      } catch (error) {
        console.error("Error fetching role details:", error);
      }
    },
  },
  mounted() {
    this.getAllRoles();
  },
  created() {
    this.$root.$on('searchQueryForRolesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllRoles();
    })
  }
};
</script>
<style></style>

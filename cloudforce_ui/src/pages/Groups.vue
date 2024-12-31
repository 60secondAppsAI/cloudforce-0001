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
            <group-table
            v-if="groups && groups.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:groups="groups"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-groups="getAllGroups"
             >

            </group-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import GroupTable from "@/components/GroupTable";
import GroupService from "../services/GroupService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    GroupTable,
  },
  data() {
    return {
      groups: [],
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
    async getAllGroups(sortBy='groupId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await GroupService.getAllGroups(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.groups.length) {
					this.groups = response.data.groups;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching groups:", error);
        }
        
      } catch (error) {
        console.error("Error fetching group details:", error);
      }
    },
  },
  mounted() {
    this.getAllGroups();
  },
  created() {
    this.$root.$on('searchQueryForGroupsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllGroups();
    })
  }
};
</script>
<style></style>

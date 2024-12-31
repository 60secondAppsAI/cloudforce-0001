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
            <task-table
            v-if="tasks && tasks.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:tasks="tasks"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-tasks="getAllTasks"
             >

            </task-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import TaskTable from "@/components/TaskTable";
import TaskService from "../services/TaskService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    TaskTable,
  },
  data() {
    return {
      tasks: [],
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
    async getAllTasks(sortBy='taskId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await TaskService.getAllTasks(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.tasks.length) {
					this.tasks = response.data.tasks;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching tasks:", error);
        }
        
      } catch (error) {
        console.error("Error fetching task details:", error);
      }
    },
  },
  mounted() {
    this.getAllTasks();
  },
  created() {
    this.$root.$on('searchQueryForTasksChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllTasks();
    })
  }
};
</script>
<style></style>

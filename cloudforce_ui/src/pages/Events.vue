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
            <event-table
            v-if="events && events.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:events="events"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-events="getAllEvents"
             >

            </event-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import EventTable from "@/components/EventTable";
import EventService from "../services/EventService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    EventTable,
  },
  data() {
    return {
      events: [],
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
    async getAllEvents(sortBy='eventId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await EventService.getAllEvents(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.events.length) {
					this.events = response.data.events;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching events:", error);
        }
        
      } catch (error) {
        console.error("Error fetching event details:", error);
      }
    },
  },
  mounted() {
    this.getAllEvents();
  },
  created() {
    this.$root.$on('searchQueryForEventsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllEvents();
    })
  }
};
</script>
<style></style>

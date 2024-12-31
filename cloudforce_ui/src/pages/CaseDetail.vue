
	<template>
		<div class="content">


				<table>
					<tr> 
						<td style="width: 100%;"></td>
				        	<td style="width: 150px;">
				      			<base-button class="btn btn-primary" @click="updateCase()">Save</base-button>
				        	</td>
				        	<td style="width: 150px;">
				        	<td style="width: 150px;">
				        		
								<div >
								  <div class="container">
												    <base-button class="btn btn-primary" @click="modal2Cases = true">Add</base-button>
								              <modal :show.sync="modal2Cases">
								                <template slot="header">
								                  <h5 class="modal-title" id="exampleModalLabel">Add Case</h5>
								                </template>
								                <div>
								                  <form @submit.prevent>
								  <base-input label="CaseId" type="text" placeholder="Enter CaseId" v-model="caseToAdd.caseId"></base-input>
								  <base-input label="Description" type="text" placeholder="Enter Description" v-model="caseToAdd.description"></base-input>
								  <base-input label="Status" type="text" placeholder="Enter Status" v-model="caseToAdd.status"></base-input>
								  <base-input label="Priority" type="text" placeholder="Enter Priority" v-model="caseToAdd.priority"></base-input>
												                  </form>
								                </div>
								                <template slot="footer">
								                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
								                </template>
								              </modal>
												  </div>
								</div>
				        	</td>
					</tr> 
				</table>



				<table>
      <template>
        <div class="content">
          <a-modal :footer="null" :visible="showProgressBar" class="centered-modal" v-if="showProgressBar"
                   :closable="false">
            <div style="text-align: center; padding: 20px;">
              <h5>Please wait...</h5>
            </div>
          </a-modal>
        </div>
        <div class="content">
          <a-modal :footer="null" :visible="userPermissionDialog" :closable="false" class="centered-modal" v-if="userPermissionDialog">
            <div style="text-align: center; padding: 20px;">
              <h5>User does not have permission</h5>
              <button @click="handlePermissionDialogClose">OK</button>
            </div>
          </a-modal>
        </div>
      </template>

	
	
	
		
		
										<tr> 
					<td class="detail_view_column_2">Case Id</td>
										    <td class="detail_view_column_1">
							<div class="detail_view_input" style="display: inline-flex;">
																		<input label="CaseId" type="text" placeholder="Enter CaseId" v-model="caseDetails.caseId"></input>
																		</div>
						</td>
														</tr>
															<tr> 
					<td class="detail_view_column_2">Description</td>
										    <td class="detail_view_column_1">
							<div class="detail_view_input" style="display: inline-flex;">
																		<input label="Description" type="text" placeholder="Enter Description" v-model="caseDetails.description"></input>
																		</div>
						</td>
														</tr>
															<tr> 
					<td class="detail_view_column_2">Status</td>
										    <td class="detail_view_column_1">
							<div class="detail_view_input" style="display: inline-flex;">
																		<input label="Status" type="text" placeholder="Enter Status" v-model="caseDetails.status"></input>
																		</div>
						</td>
														</tr>
															<tr> 
					<td class="detail_view_column_2">Priority</td>
										    <td class="detail_view_column_1">
							<div class="detail_view_input" style="display: inline-flex;">
																		<input label="Priority" type="text" placeholder="Enter Priority" v-model="caseDetails.priority"></input>
																		</div>
						</td>
														</tr>
						

	
		
		</table>
	
	




















			<a-tabs v-model:activeKey="activeKey">
											</a-tabs>

		</div>
	</template>

<script>

import CaseService from "../services/CaseService";
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/Card";
import { ASelect, ASelectOption, AButton, Table, Pagination, message } from "ant-design-vue";
import { VueAutosuggest } from "vue-autosuggest";
import { UploadOutlined } from '@ant-design/icons-vue';
import Datepicker from 'vuejs-datepicker';


const tableColumns = [];
const tableData = [];
const fileList = ([]);
let file = ({});
const checked = (false);
let yearValue = ('');

export default {
  props: {
    caseId: {
      type: String,
      required: true
    }
  },
  components: {
		Modal,
		BaseButton,
		BaseInput,
		VueAutosuggest,
		UploadOutlined,
		Datepicker,
  },
  data() {
    return {
      caseToAdd: {},
      modal2Cases: false,
      caseDetails: null,


      fileList,
      checked,
	  yearValue,


    };
  },
  methods: {

    

	
	handleSwitchChange(lifAnswerId, checked) {
	    this.answers.forEach(function(answer) {
	    	if (answer.questionType == '2') {
		    	if (answer.lifAnswerId == lifAnswerId) {
					answer.answerb = checked;
					if ( checked) {
						answer.answer = "Yes";
					} else {
						answer.answer = "No";
					}
				}
	    	}
		});
	},


    async handleAddSubmitted() {
      this.modal2Cases = false;

      const jsonData = JSON.stringify(this.caseToAdd);
      console.log(jsonData);
      
      const res = await CaseService.addCase(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},

    async updateCase() {

      const jsonData = JSON.stringify(this.caseDetails);
      const res = await CaseService.update(jsonData);

      if (res.status === 200) {
//        this.$notify({
//          component: NotificationTemplate,
//          icon: "tim-icons icon-bell-55",
//          type: "success",
//          timeout: 3000,
//        });

//        this.modalCases = false;
//        this.getAllCases();
      }
    },


    async getCaseDetails() {
      try {
			let response = await CaseService.get(this.caseId);
			this.caseDetails = response.data;

			let hasAnswers = 0;



			if (hasAnswers) {			
		        this.answers.forEach(function(answer) {
		            if (answer.answer === "Yes") {
		               temp = true;
		            } else {
		               temp = false;
		            }
		        });
			}


	    
      } catch (error) {
        console.error('Error fetching case details:', error);
      }
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
    this.getCaseDetails();
  },
  computed: {
  },
  
};
</script>
<style>

.detail_view_input input {
	text-align: center;
}

.detail_view_column_1 {
	width: 250px; 
	text-align: center;
}
.detail_view_column_2 {
	min-width: 250px; 
}
.input {
	text-align: center;
}
#autosuggest__input {
  display: inline-block;
}
.autosuggest__results-container {
	position: relative;
}
.autosuggest__results {
	font-weight: 300;
	margin: 0;
	position: absolute;
	z-index: 10000001;
	border: 1px solid #e0e0e0;
	border-bottom-left-radius: 4px;
	border-bottom-right-radius: 4px;
	background: white;
}
.autosuggest__results ul {
	list-style: none;
	padding-left: 0;
	margin: 0;
}
.autosuggest__results .autosuggest__results-item {
	cursor: pointer;
	padding: 5px;
}
#autosuggest ul:nth-child(1) > .autosuggest__results_title {
	border-top: none;
}
.autosuggest__results .autosuggest__results_title {
	color: gray;
	font-size: 11px;
	margin-left: 0;
	padding: 15px 13px 5px;
	border-top: 1px solid lightgray;
}
.autosuggest__results .autosuggest__results-item:active,
.autosuggest__results .autosuggest__results-item:hover,
.autosuggest__results .autosuggest__results-item:focus,
.autosuggest__results
.autosuggest__results-item.autosuggest__results-item--highlighted {
	background-color: #F6F6F6;
}

.case-image {
				    	height: 250px;
			}

.inline-item {
  display: inline-block;
  margin-right: 100px; /* Adjust margin as needed */
}

.switch-container {
	width: 215px; 
    text-align: right;
    margin-right: 15px;
    margin-top: 5px;
    margin-bottom: 5px;
}



</style>

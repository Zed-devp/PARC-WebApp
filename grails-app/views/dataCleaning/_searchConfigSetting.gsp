<div class="panel panel-primary">
				<div class="panel-heading">Searching Parameter Configuration:</div>
				<div class="panel-body">
					<div class="row">
      					<div class="col-xs-6">
				          	<label>Start Temperature:</label> 
				          	<div class="range range-primary">
				          		<input id="stTemp" type="range" name="stTemp" min="0.0" max="1.0" value="${config["stTemp"] }" step="0.00001" onchange="stTempText.value=value" />
				          		<output id="stTempText">${config["stTemp"] }</output>
				       		</div>
		       		   	</div>
			       		
			       		<div class="col-xs-6">
				       		<label>Final Temperature:</label> 
				       		<div class="range range-primary">
					        	<input id="endTemp" type="range" name="endTemp" min="0.0" max="1.0" value="${config["endTemp"] }" step="0.00001" onchange="endTempText.value=value" />
					        	<output id="endTempText">${config["endTemp"] }</output>
				        	</div>
			        	</div>
		        	</div>
		        	
		        	<div class="row">
      					<div class="col-xs-6">
				        	<label>Alpha Temperature:</label> 
				        	<div class="range range-primary">
					        	<input id="alpTemp" type="range" name="alpTemp" min="0.0" max="1.0" value="${config["alpTemp"] }" step="0.001" onchange="alpTempText.value=value" />
					        	<output id="alpTempText">${config["alpTemp"] }</output>
					        </div>
				        </div>
			        
			        	<div class="col-xs-6">
					        <label>Step per Temperature:</label> 
					        <div class="range range-primary">
					        	<input id="stepTemp" type="range" name="stepTemp" min="0.0" max="10" value="${config["stepTemp"] }" step="1" onchange="stepTempText.value=value" />
					        	<output id="stepTempText">${config["stepTemp"] }</output>
					        </div>
				        </div>
			        </div>
			        
			        <div class="row">
      					<div class="col-xs-6">
					       <label>Best Energy:</label> 
					        <div class="range range-primary">
					        	<input id="bestEn" type="range" name="bestEn" min="0.0" max="1.0" value="${config["bestEn"] }" step="0.00001" onchange="bestEnText.value=value" />
					        	<output id="bestEnText">${config["bestEn"] }</output>
					        </div>
				        </div>
			        </div>
		        </div>
	        </div>
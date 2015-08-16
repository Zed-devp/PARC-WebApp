<div class="panel panel-primary">
				<div class="panel-heading">Searching Parameter Configuration:</div>
				<div class="panel-body">
					<div class="row">
      					<div class="col-xs-6">
				          	<label>Start Temperature:</label> 
				          	<div class="range range-primary">
				          		<input id="stTemp" type="range" name="stTemp" min="0.0" max="1.0" value="0.0009" step="0.00001" onchange="stTempText.value=value" />
				          		<output id="stTempText">0.0009</output>
				       		</div>
		       		   	</div>
			       		
			       		<div class="col-xs-6">
				       		<label>Final Temperature:</label> 
				       		<div class="range range-primary">
					        	<input id="endTemp" type="range" name="endTemp" min="0.0" max="1.0" value="0.00005" step="0.00001" onchange="endTempText.value=value" />
					        	<output id="endTempText">0.00005</output>
				        	</div>
			        	</div>
		        	</div>
		        	
		        	<div class="row">
      					<div class="col-xs-6">
				        	<label>Alpha Temperature:</label> 
				        	<div class="range range-primary">
					        	<input id="alpTemp" type="range" name="alpTemp" min="0.0" max="1.0" value="0.99" step="0.001" onchange="alpTempText.value=value" />
					        	<output id="alpTempText">0.99</output>
					        </div>
				        </div>
			        
			        	<div class="col-xs-6">
					        <label>Step per Temperature:</label> 
					        <div class="range range-primary">
					        	<input id="stepTemp" type="range" name="stepTemp" min="0.0" max="10" value="1" step="1" onchange="stepTempText.value=value" />
					        	<output id="stepTempText">1</output>
					        </div>
				        </div>
			        </div>
			        
			        <div class="row">
      					<div class="col-xs-6">
					       <label>Best Energy:</label> 
					        <div class="range range-primary">
					        	<input id="bestEn" type="range" name="bestEn" min="0.0" max="1.0" value="0.00001" step="0.00001" onchange="bestEnText.value=value" />
					        	<output id="bestEnText">0.00001</output>
					        </div>
				        </div>
			        </div>
		        </div>
	        </div>
/*
   Copyright 2021 WeAreFrank!

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.nn.adapterframework.util.flow;

import java.io.IOException;

import nl.nn.adapterframework.configuration.AdapterLifecycleWrapperBase;
import nl.nn.adapterframework.configuration.ConfigurationWarnings;
import nl.nn.adapterframework.configuration.SuppressKeys;
import nl.nn.adapterframework.core.Adapter;
import nl.nn.adapterframework.util.AppConstants;

public class FlowDiagramAdapterWrapper extends AdapterLifecycleWrapperBase {
	private FlowDiagramManager flowDiagramManager;
	private boolean suppressWarnings = AppConstants.getInstance().getBoolean(SuppressKeys.FLOW_GENERATION_ERROR.getKey(), false);

	@Override
	public void addAdapter(Adapter adapter) {
		if (flowDiagramManager != null) {
			try {
				flowDiagramManager.generate(adapter);
			} catch (IOException e) { //Exception is already logged when loglevel equals debug (see FlowDiagramManager#generateFlowDiagram(String, String, File))
				if(!suppressWarnings) {
					ConfigurationWarnings.add(adapter, log, "error generating flow diagram", e);
				}
			}
		}
	}

	@Override
	public void removeAdapter(Adapter adapter) {
		//no need to do anything here
	}

	public void setFlowDiagramManager(FlowDiagramManager flowDiagramManager) {
		this.flowDiagramManager = flowDiagramManager;
	}
}
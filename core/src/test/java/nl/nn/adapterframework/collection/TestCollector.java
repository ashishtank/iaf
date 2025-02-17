package nl.nn.adapterframework.collection;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import nl.nn.adapterframework.core.PipeLineSession;
import nl.nn.adapterframework.parameters.ParameterValueList;
import nl.nn.adapterframework.stream.Message;

public class TestCollector implements ICollector<TestCollectorPart> {

	boolean open=true;
	private StringWriter input = new StringWriter();

	public String getInput() {
		return input.toString();
	}

	@Override
	public TestCollectorPart createPart(Message input, PipeLineSession session, ParameterValueList pvl) throws CollectionException {
		try {
			this.input.write(input.asString());
			if (input.asString().equals("exception")) {
				throw new CollectionException("TestCollector exception");
			}
		} catch (IOException e) {
			throw new CollectionException(e);
		}
		return new TestCollectorPart(input);
	}

	@Override
	public Message build(List<TestCollectorPart> parts) throws IOException {
		StringWriter input = new StringWriter();
		for(TestCollectorPart part : parts) {
			input.append(part.asString());
		}

		return Message.asMessage(parts.size() + ":" + input.toString());
	}

	@Override
	public void close() throws Exception {
		open=false;
	}
}

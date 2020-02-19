package resources.models;

import java.util.ArrayList;
import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("DocumentData")
public class DocumentData {
	
	@XStreamImplicit(itemFieldName = "Document")
	private List<Document> documents = new ArrayList<Document>();

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

}

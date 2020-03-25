package resources.models;

import java.util.ArrayList;
import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("DocumentTypeData")
public class DocumentTypeData {
	
	@XStreamImplicit(itemFieldName = "DocumentType")
	private List<DocumentType> documents = new ArrayList<DocumentType>();

	public List<DocumentType> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentType> documents) {
		this.documents = documents;
	}

}

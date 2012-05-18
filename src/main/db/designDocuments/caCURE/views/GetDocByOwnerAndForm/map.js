function(doc)
{  
    if (doc.ownerId && doc.formId)
 	{   
	    emit([doc.ownerId, doc.formId], doc);
	}
}
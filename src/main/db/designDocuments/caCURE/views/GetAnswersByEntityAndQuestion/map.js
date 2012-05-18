function(doc)
{
    if(doc.entityId)
  	{
	    var questions = doc["questions"];
		if (questions)
		{
		    for(key in questions)
            {
			    /* get question data */
				var question=questions[key];
                var answers = question.answerValues;
                if(answers)
				{
                    var answersArray = new Array();
                    for(var i=0; i<answers.length; i++)
                    {
                        answersArray[i] = answers[i].ansValue;
                    }
                    emit([doc.entityId, key], answersArray);
                }
			}
		}
        
    }
}
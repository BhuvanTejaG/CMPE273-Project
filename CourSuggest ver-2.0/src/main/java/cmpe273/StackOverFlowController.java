package cmpe273;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.ArrayList;

@ComponentScan("model")
@Controller
@SessionAttributes("userObj")
public class StackOverFlowController {
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute StackOverFlow stackoverflow, @ModelAttribute StackOverFlowOutPut stackOverFlowOutPut,
                             Model model)  {
     //this will be used for REST service - POST
		
        return NULL; 
	 


    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String stackOverFlowForm(Model model, StackOverFlow stackoverflow) {
        //this will be used for REST service - GET
	StackOverFlow stackOverFlow = new StackOverFlow();
        /*
         * Replaced this list with list of Stack Over Flow skills populate it.
         * It will call with get request. Or when the url is called first time.
         */
        List<String> skills = new ArrayList<String>();
        skills=stackOverFlowData();
       /* skills.add("Java");
        skills.add("Python");
        skills.add("Scala");*/
        stackOverFlow.setSkillList(skills);
        model.addAttribute("stackOverFlow", stackOverFlow);
        return "stackOverFlow";

    }

}

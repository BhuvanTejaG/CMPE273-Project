package cmpe273;

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
		
        return NULL;

    }

}

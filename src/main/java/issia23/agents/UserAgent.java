package issia23.agents;

import issia23.data.Product;
import jade.core.AID;
import jade.core.AgentServicesTools;
import jade.domain.FIPANames;
import jade.gui.AgentWindowed;
import jade.gui.GuiEvent;
import jade.gui.SimpleWindow4Agent;
import jade.lang.acl.ACLMessage;
import issia23.behaviours.ContacterRepairCafe;

import java.io.IOException;
import java.util.*;

public class UserAgent extends AgentWindowed {
    /**
     * skill in "repairing" from 0 (not understand) to 3 (repairman like)
     */
    int skill;
    /**
     * list of potential helpers (agent registered in the type of service "repair"
     */
    List<AID> helpers;

    List<Product> products;

    @Override
    public void setup() {
        this.window = new SimpleWindow4Agent(getLocalName(), this);
        window.setButtonActivated(true);

        skill = (int) (Math.random() * 4);
        println("hello, I have a skill = " + skill);
        helpers = new ArrayList<>();

        products = new ArrayList<>();
        var allProducts = Product.getListProducts();
        var nb = allProducts.size();
        for(int i=0; i<3; i++) {
            var rand = (int)(Math.random()*nb);
            products.add(allProducts.get(rand));
        }
        println("i have the following products : ");
        for(var p:products) println(p.getName() + " ");
    }

    @Override
    public void onGuiEvent(GuiEvent evt) {
        //I suppose there is only one type of event, clic on go
        //search about repairing agents
        helpers.addAll(Arrays.stream(AgentServicesTools.searchAgents(this, "repair", null)).toList());

        println("-".repeat(30));
        for (AID aid : helpers)
            println("found this agent : " + aid.getLocalName());
        println("-".repeat(30));

        try {
            addCFP();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**add a CFP from user to list of helpers*/
    private void addCFP() throws IOException {
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        msg.setConversationId("id");
        int randint = (int)(Math.random()*products.size());
        msg.setContentObject(products.get(randint));

        msg.addReceivers(helpers.toArray(AID[]::new));
        println("-".repeat(40));

        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        msg.setReplyByDate(new Date(System.currentTimeMillis() + 1000));

        var contacterRepairCafe = new ContacterRepairCafe(this, msg);
        addBehaviour(contacterRepairCafe);
    }

    public void println(String s){
        window.println(s);
    }

    @Override
    public void takeDown(){println("bye !!!");}
}

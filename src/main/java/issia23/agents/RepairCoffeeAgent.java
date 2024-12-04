package issia23.agents;

import jade.core.AgentServicesTools;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.gui.AgentWindowed;
import jade.gui.SimpleWindow4Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepairCoffeeAgent extends AgentWindowed {
    @Override
    public void setup(){
        this.window = new SimpleWindow4Agent(getLocalName(),this);
        this.window.setBackgroundTextColor(Color.orange);
        println("hello, do you want coffee ?");

        //registration to the yellow pages (Directory Facilitator Agent)
        AgentServicesTools.register(this, "repair", "coffee");
        println("I'm just registered as a repair-coffee");

        addListeningACFP();

    }

    private void addListeningACFP()
    {

        MessageTemplate model = MessageTemplate.MatchConversationId("id");

        ContractNetResponder behaviourRepair = new ContractNetResponder(this, model) {

            //function triggered by a PROPOSE msg : send back the ranking
            @Override
            protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
                println("~".repeat(40));
                println(cfp.getSender().getLocalName() + " is asking for a repair for: " + cfp.getContent());

                int randomAvailability = (int) (Math.random() * 8) - 4; // Random to simulate availability
                ACLMessage answer = cfp.createReply();

                if (randomAvailability <= 0) {
                    answer.setPerformative(ACLMessage.REFUSE);
                    println("Refused repair for: " + cfp.getContent());
                } else {
                    int cost = 10; // Fixed cost for repair coffee
                    int time = 2;  // Fixed time for repair coffee
                    answer.setPerformative(ACLMessage.PROPOSE);
                    answer.setContent(cost + "," + time); // Propose cost and time as a string
                    println("Proposing repair for " + cfp.getContent() + " with cost: " + cost + "€ and time: " + time + " days.");
                }

                return answer;
            }

            /**proposals in the form option1,option2,option3,option4,.....
             * * he returns his choice by ordering the options and giving their positions
             * @param offres list of proposals in the form of option1,option2,option3,option4
             * @return orderly choice in the form of option2_1,option4_2,option3_3,option1_4
             * */
            private String makeItsChoice(String offres) {
                ArrayList<String> choice = new ArrayList<>(List.of(offres.split(",")));
                Collections.shuffle(choice);
                StringBuilder sb = new StringBuilder();
                String pref = ">";
                for (String s : choice) sb.append(s).append(pref);
                String proposition  = sb.substring(0, sb.length()-1);
                println("I propose this ranking: " + proposition);
                return proposition;
            }

            //function triggered by a ACCEPT_PROPOSAL msg : the polling station agent  accept the vote
            //@param cfp : the initial cfp message
            //@param propose : the proposal I sent
            //@param accept : the acceptation sent by the auctioneer
            @Override
            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
                println("=".repeat(15));
                println(" I proposed " + propose.getContent());
                println(cfp.getSender().getLocalName() + " accepted my poposam and sent the result:  " + accept.getContent());
                ACLMessage msg = accept.createReply();
                msg.setPerformative(ACLMessage.INFORM);
                msg.setContent("ok !");
                return msg;
            }

            //function triggered by a REJECT_PROPOSAL msg : the auctioneer rejected my vote !
            //@param cfp : the initial cfp message
            //@param propose : the proposal I sent
            //@param accept : the reject sent by the auctioneer
            @Override
            protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
                println("=".repeat(10));
                println("PROPOSAL REJECTED");
                println(cfp.getSender().getLocalName() + " asked to repair elt no " + cfp.getContent());
                println(" I proposed " + propose.getContent());
                println(cfp.getSender().getLocalName() + " refused ! with this message: " + reject.getContent());
            }


        };

        addBehaviour(behaviourRepair);
    }

}

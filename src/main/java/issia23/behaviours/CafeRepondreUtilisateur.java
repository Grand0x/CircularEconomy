package issia23.behaviours;

import issia23.agents.RepairCoffeeAgent;
import issia23.data.Product;
import issia23.data.Part;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;

import java.util.*;

public class CafeRepondreUtilisateur extends ContractNetResponder {

    RepairCoffeeAgent monAgent;

    public CafeRepondreUtilisateur(RepairCoffeeAgent a, MessageTemplate model) {
        super(a, model);
        monAgent = a;
    }

    //function triggered by a PROPOSE msg : send back the ranking
    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        monAgent.println("~".repeat(40));
        ACLMessage answer = cfp.createReply();

        try {
            Product productDTO = (Product) cfp.getContentObject();
            monAgent.println(cfp.getSender().getLocalName() + " wan't to repair the product " + productDTO.getName());


            // Search for the part in the list of available parts
            Part partToRepair = productDTO.getFaultyPart();

            Part part = monAgent.findPart(partToRepair.getName());

            if (part == null) {
                // If the part is not available, we refuse the request
                monAgent.println("Sorry, i don't have this part.");
                answer.setPerformative(ACLMessage.REFUSE);
                return answer;
            }

            int price = (int)part.getPrice();

            // Response with the proposed price
            answer.setPerformative(ACLMessage.PROPOSE);
            answer.setContent(String.valueOf(price));

            monAgent.println("I can repair for " + price + " €.");

            return answer;
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }
    }

    //function triggered by a ACCEPT_PROPOSAL msg : the polling station agent  accept the vote
    //@param cfp : the initial cfp message
    //@param propose : the proposal I sent
    //@param accept : the acceptation sent by the auctioneer
    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        monAgent.println("=".repeat(15));
        monAgent.println("I proposed " + propose.getContent());
        monAgent.println(cfp.getSender().getLocalName() + " accepted my proposal with the response: " + accept.getContent());

        ACLMessage msg = accept.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        msg.setContent("Repair accepted!");
        return msg;
    }

    //function triggered by a REJECT_PROPOSAL msg : the auctioneer rejected my vote !
    //@param cfp : the initial cfp message
    //@param propose : the proposal I sent
    //@param accept : the reject sent by the auctioneer
    @Override
    protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
        monAgent.println("=".repeat(10));
        monAgent.println("PROPOSAL REJECTED");
        monAgent.println(cfp.getSender().getLocalName() + " rejected my proposal: " + reject.getContent());
    }


};

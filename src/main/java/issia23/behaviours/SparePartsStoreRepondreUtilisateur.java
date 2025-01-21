package issia23.behaviours;

import issia23.data.Product;
import jade.proto.ContractNetResponder;
import issia23.agents.SparePartsStoreAgent;
import issia23.data.Part;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
public class SparePartsStoreRepondreUtilisateur extends ContractNetResponder {

    SparePartsStoreAgent monAgent;


    public SparePartsStoreRepondreUtilisateur(SparePartsStoreAgent a, MessageTemplate model) {
        super(a, model);
        monAgent = a;
    }

    // Function triggered by a CFP message: respond to a spare part request
    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        monAgent.println("~".repeat(40));
        ACLMessage answer = cfp.createReply();

        try {
            // The CFP message contains a reference to the requested part
            Product productDTO = (Product) cfp.getContentObject();
            monAgent.println(cfp.getSender().getLocalName() + " requests a repair for the product of type " + productDTO.getName());

            Part partToRepair = productDTO.getFaultyPart();
            monAgent.println("The part to be repaired is: " + partToRepair.getName());

            // Search for the part in the store's inventory
            Part requestedPart = monAgent.findPart(partToRepair.getName());

            if (requestedPart == null) {
                // If the part is not available, we refuse the request
                monAgent.println("Sorry, I don't have this part.");
                answer.setPerformative(ACLMessage.REFUSE);
                return answer;
            }

            // Calculate the price (between 20 and 40 €)
            int price = (int)requestedPart.getPrice();

            // Response with the proposed price for the part
            answer.setPerformative(ACLMessage.PROPOSE);
            answer.setContent(String.valueOf(price));  // Le prix proposé pour la pièce

            monAgent.println("I propose a price of " + price + " € for the spare part.");

            return answer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Function triggered by an ACCEPT_PROPOSAL message: the user accepts the offer
    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        monAgent.println("=".repeat(15));
        monAgent.println("I proposed " + propose.getContent());
        monAgent.println(cfp.getSender().getLocalName() + " accepted my proposal.");

        ACLMessage msg = accept.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        msg.setContent("Spare part accepted and reserved!");
        return msg;
    }

    // Function triggered by a REJECT_PROPOSAL message: the user rejects the offer
    @Override
    protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
        monAgent.println("=".repeat(10));
        monAgent.println("PROPOSAL REJECTED");
        monAgent.println(cfp.getSender().getLocalName() + " rejected.");
    }

}


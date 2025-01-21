package issia23.behaviours;

import jade.proto.ContractNetResponder;
import issia23.agents.DistributorAgent;
import issia23.data.Product;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DistributorRepondreUtilisateur extends ContractNetResponder {

    DistributorAgent monAgent;


    public DistributorRepondreUtilisateur(DistributorAgent a, MessageTemplate model) {
        super(a, model);
        monAgent = a;
    }

    // Function triggered by a CFP message: respond to a product replacement request
    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        monAgent.println("~".repeat(40));
        ACLMessage answer = cfp.createReply();

        try {
            // The CFP message contains a reference to the requested product
            Product productDTO = (Product) cfp.getContentObject();
            monAgent.println(cfp.getSender().getLocalName() + " requests a replacement for the product: " + productDTO.getName());

            // Search for the product in the distributor's inventory
            Product requestedProduct = monAgent.findProduct(productDTO.getName());

            if (requestedProduct == null) {
                // If the product is not available, we refuse the request
                monAgent.println("Sorry, I don't have this product.");
                answer.setPerformative(ACLMessage.REFUSE);
                return answer;
            }

            int price = (int)requestedProduct.getPrice();

            // Response with the proposed price for the product
            answer.setPerformative(ACLMessage.PROPOSE);
            answer.setContent(String.valueOf(price));

            monAgent.println("I propose a price of " + price + " € for the product.");

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
        msg.setContent("Product accepted and reserved!");
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


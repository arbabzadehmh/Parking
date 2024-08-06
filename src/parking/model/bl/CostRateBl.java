package parking.model.bl;

import parking.controller.exception.DuplicateException;

import parking.controller.exception.NoContentException;
import parking.model.da.CostRateDa;

import parking.model.entity.CostRate;

import java.util.List;

public class CostRateBl {

    public static CostRate save(CostRate costRate) throws Exception {
        try (CostRateDa costRateDa = new CostRateDa()) {
            if (costRateDa.findAll().isEmpty()) {
                return costRateDa.save(costRate);
            }
            throw new DuplicateException("Cost Rate is exist !");
        }
    }

    public static CostRate edit(CostRate costRate) throws Exception {
        try (CostRateDa costRateDa = new CostRateDa()) {
            if (!(costRateDa.findAll().isEmpty())) {
                return costRateDa.edit(costRate);
            }
            throw new DuplicateException("There is no Cost Rate !");
        }
    }

    public static List<CostRate> findAll() throws Exception {
        try (CostRateDa costRateDa = new CostRateDa()) {
            List<CostRate> costRates = costRateDa.findAll();
            if (!(costRates.isEmpty())) {
                return costRates;
            }
            throw new DuplicateException("The list is empty !");
        }
    }

    public static CostRate find() throws Exception {
        try (CostRateDa costRateDa = new CostRateDa()) {
            CostRate costRate = costRateDa.find();
            if (costRate != null) {
                return costRate;
            }
            throw new NoContentException("Cost Rate not found !");
        }
    }
}

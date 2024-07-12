package com.prectinkerers.inventorymanagementsystem.dao.LabratoryTools;

import com.prectinkerers.inventorymanagementsystem.dao.utilization.Utilization;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Desk3DPrinter implements Tools {

    @OneToMany
    List<Utilization> utilizations;
}

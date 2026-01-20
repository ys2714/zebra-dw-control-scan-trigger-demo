# Un-Official Zebra DataWedge Control Scan Trigger Behavior Demo

a simple demo to show how to customize hardware and software scan trigger behaviors.

## Prepare Profile

please manually create a profile named "control_scan_trigger" with following settings:

### 1.Barcode Input

1.1 Enabled = true

1.2 Hardware Trigger = true

1.3 Config Scanner Settings

1.3.1 Reader Parameters

1.3.1.1 Aim Type = Timed Continuous (the last one in the list)

### 2.Keystroke Output

2.1 Enabled = false

### 3.Intent Output

3.1 Enabled = true

3.2 Intente Action = com.zebra.trigger.ACTION

3.3 Intent Category = com.zebra.trigger.CATEGORY

3.4 Intent Delivery = Broadcast Intent

## For more complex use case

please refer to following repo:
https://github.com/ys2714/zebra-sdk-kotlin-wrapper

classes: 
- DataWedgeTriggerActivity.kt
- DataWedgeTriggerViewModel.kt

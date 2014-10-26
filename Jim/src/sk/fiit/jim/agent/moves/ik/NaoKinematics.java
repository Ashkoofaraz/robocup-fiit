package sk.fiit.jim.agent.moves.ik;

import java.util.List;

import sk.fiit.robocup.library.geometry.Vector3D;

public class NaoKinematics
{
    static class KMatTable
    {
        GenMatrix A;
        GenMatrix B;
        boolean aisZero, aisIdentity, bisZero;
        
        KMatTable add(KMatTable t) {
            return null;
        }
        
        KMatTable sub(KMatTable t) {
            return null;
        }
        
        KMatTable mult(KMatTable t) {
            return null;
        }
        
        KMatTable div(KMatTable t) {
            return null;
        }
    }
    
    private static class GenMatrix
    {
        
    }
    private static class AngleType
    {
        float value;
    }
    
    private static class AngleContainer
    {
        List<List<AngleType>> value;
    }
    
    private static class FKvars
    {
        Vector3D p;
        Vector3D a;
    }
    
    KMatTable getForwardEffector()
    {
        
        return null;
    }
    
    
    
//    boolean setJoints(List<AngleType> jointsset){ /*..*/    }
//    boolean setChain(KDeviceLists::ChainsNames ch, List<AngleType> jointsset);

    
//    KMatTable getForwardFromTo(Effectors start, Effectors stop);

//    Vector3D calculateCenterOfMass()
//    {
//    }

//    AngleContainer inverseLeftHand(FKvars s) {/*...*/}
//    AngleContainer inverseLeftHand(KMatTable targetPoint) {/*...*/}
//    AngleContainer jacobianInverseLeftHand(FKvars s) {/*...*/}
//    AngleContainer jacobianInverseLeftHand(KMatTable targetPoint) {/*...*/}

    //Jacobian Inverse Kinematics
//    private   AngleContainer jacobianInverseHead(KMatTable targetPoint, KDeviceLists::ChainsNames ch, bool topCamera) {/*...*/}
//    private  AngleContainer jacobianInverseHands(KMatTable targetPoint, KDeviceLists::ChainsNames ch){/*...*/}
//    private AngleContainer jacobianInverseLegs(KMatTable targetPoint, KDeviceLists::ChainsNames ch){/*...*/}
    
    
//    static KMatTable getTransformation( FKvars s)
//    {
//        KMatTable  T;
//        KMatTransf::makeTransformation(T, s.p(0), s.p(1), s.p(2), s.a(0), s.a(1), s.a(2));
//        return T;
//    }
    
//    static void mirrorTransformation(KMatTable  targetPoint)
//    {
//        //This can be proved by the analytical construction of a rotation matrix from axis-angle rotation
//        targetPoint(0,1)=-targetPoint(0,1);
//        //targetPoint(0,2)=-targetPoint(0,2);
//        targetPoint(1,0)=-targetPoint(1,0);
//        targetPoint(1,2)=-targetPoint(1,2);
//        //targetPoint(2,0)=-targetPoint(2,0);
//        targetPoint(2,1)=-targetPoint(2,1);
//        //Mirror Y translation
//        targetPoint(1,3)=-targetPoint(1,3);
//    }
        
}

import React, { useState } from 'react';
import '../assets/styles/Membership.css';
import { Check, X } from 'lucide-react';

const Membership = () => {
  const [billingCycle, setBillingCycle] = useState('monthly'); // 'monthly' or 'annual'
  
  const handleBillingCycleChange = (cycle) => {
    setBillingCycle(cycle);
  };
  
  // Define the plans with features and pricing
  const plans = [
    {
      name: 'Subscription for 12 months',
      description: 'Perfect for beginners starting their fitness journey',
      monthlyPrice: 26.50,
      annualPrice: 270,
      features: [
        { name: 'Access to basic workout plans', included: true },
        { name: 'Basic fitness tracking', included: true },
        { name: 'Community forum access', included: true },
        { name: 'Email support', included: true },
        { name: 'Personal trainer consultations', included: false },
        { name: 'Video training sessions', included: false },
        { name: 'Nutrition planning', included: false },
        { name: 'Premium content access', included: false }
      ],
      buttonVariant: 'secondary'
    },
    {
      name: 'Subscription for 6 months',
      description: 'Our most popular plan for serious fitness enthusiasts',
      monthlyPrice: 30.50,
      annualPrice: 310,
      features: [
        { name: 'Access to basic workout plans', included: true },
        { name: 'Advanced fitness tracking', included: true },
        { name: 'Community forum access', included: true },
        { name: 'Priority email support', included: true },
        { name: 'Monthly personal trainer consultations', included: true },
        { name: 'Video training sessions', included: true },
        { name: 'Basic nutrition planning', included: true },
        { name: 'Premium content access', included: false }
      ],
      buttonVariant: 'primary',
      popular: true
    },
    {
      name: 'One time payment',
      description: 'The ultimate fitness experience with personalized coaching',
      monthlyPrice: 45.5,
      annualPrice: 270,
      features: [
        { name: 'Access to all workout plans', included: true },
        { name: 'Complete fitness tracking & analytics', included: true },
        { name: 'Community forum with VIP access', included: true },
        { name: 'Priority 24/7 support', included: true },
        { name: 'Weekly personal trainer consultations', included: true },
        { name: 'Unlimited video training sessions', included: true },
        { name: 'Advanced nutrition planning', included: true },
        { name: 'Premium content access', included: true }
      ],
      buttonVariant: 'secondary'
    }
  ];

  return (
    <div className="membership-container">
      <main className="membership-content">
        <section className="membership-header">
          <h1 className="membership-title">Choose Your Membership Plan</h1>
          <p className="membership-subtitle">Unlock your fitness potential with the perfect plan for your goals</p>
          
          <div className="billing-toggle">
            <span className={`billing-option ${billingCycle === 'monthly' ? 'active' : ''}`}>
              Monthly
            </span>
            <button 
              className={`toggle-switch ${billingCycle === 'annual' ? 'switched' : ''}`}
              onClick={() => handleBillingCycleChange(billingCycle === 'monthly' ? 'annual' : 'monthly')}
              aria-label="Toggle billing cycle"
            >
              <span className="toggle-knob"></span>
            </button>
            <span className={`billing-option ${billingCycle === 'annual' ? 'active' : ''}`}>
              Annual <span className="discount-badge">Save 15%</span>
            </span>
          </div>
        </section>
        
        <section className="plans-container">
          {plans.map((plan, index) => (
            <div 
              key={index} 
              className={`plan-card ${plan.popular ? 'popular-plan' : ''}`}
            >
              {plan.popular && <div className="popular-badge">Most Popular</div>}
              <div className="plan-header">
                <h2 className="plan-name">{plan.name}</h2>
                <p className="plan-description">{plan.description}</p>
              </div>
              
              <div className="plan-pricing">
                <span className="currency">$</span>
                <span className="price">
                  {billingCycle === 'monthly' 
                    ? plan.monthlyPrice.toFixed(2) 
                    : (plan.annualPrice / 12).toFixed(2)}
                </span>
                <span className="billing-period">/ month</span>
              </div>
              
              {billingCycle === 'annual' && (
                <div className="annual-price-note">
                  Billed as ${plan.annualPrice.toFixed(2)} per year
                </div>
              )}
              
              <button 
                className={`plan-button ${plan.buttonVariant === 'primary' ? 'plan-button-primary' : 'plan-button-secondary'}`}
              >
                Choose {plan.name}
              </button>
              
              <div className="features-list">
                <h3 className="features-title">What's included</h3>
                <ul>
                  {plan.features.map((feature, featureIndex) => (
                    <li key={featureIndex} className={feature.included ? 'feature-included' : 'feature-excluded'}>
                      {feature.included ? (
                        <Check size={18} className="feature-icon feature-check" />
                      ) : (
                        <X size={18} className="feature-icon feature-x" />
                      )}
                      <span>{feature.name}</span>
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          ))}
        </section>
        
        <section className="membership-faq">
          <div className="faq-content">
            <h2 className="faq-title">Frequently Asked Questions</h2>
            
            <div className="faq-grid">
              <div className="faq-item">
                <h3 className="faq-question">Can I change my plan later?</h3>
                <p className="faq-answer">
                  Yes, you can upgrade or downgrade your plan at any time. Changes will be reflected in your next billing cycle.
                </p>
              </div>
              
              <div className="faq-item">
                <h3 className="faq-question">How do I cancel my subscription?</h3>
                <p className="faq-answer">
                  You can cancel your subscription from your account settings. Cancellations will take effect at the end of your current billing period.
                </p>
              </div>
              
              <div className="faq-item">
                <h3 className="faq-question">Is there a free trial period?</h3>
                <p className="faq-answer">
                  We offer a 7-day free trial for new members on any plan. You won't be charged until the trial period ends.
                </p>
              </div>
              
              <div className="faq-item">
                <h3 className="faq-question">What payment methods do you accept?</h3>
                <p className="faq-answer">
                  We accept all major credit cards, PayPal, and Apple Pay for your convenience and security.
                </p>
              </div>
            </div>
          </div>
        </section>
        
        <section className="membership-cta">
          <div className="cta-content">
            <h2 className="cta-title">Still have questions?</h2>
            <p className="cta-description">
              Our support team is here to help you find the best plan for your fitness journey.
            </p>
            <a href="/contact" className="cta-button">Contact Support</a>
          </div>
        </section>
      </main>
    </div>
  );
};

export default Membership;